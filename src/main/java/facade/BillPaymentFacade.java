package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.BillPaymentOperation;
import model.UserAccount;
import service.BillPaymentService;
import service.UserAccountService;
import util.ConnectionClosure;
import util.TransactionManager;

import java.sql.Connection;

import static enums.DAOEnum.*;

public class BillPaymentFacade {

    private BillPaymentService billPaymentService;
    private UserAccountService userAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public BillPaymentFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setBillPaymentService(BillPaymentService billPaymentService) {
        this.billPaymentService = billPaymentService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public boolean payBill(BillPaymentOperation billPaymentOperation) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        billPaymentService.setBillPaymentDAO(factory.getBillPaymentDAO(connection, BILL_PAYMENT_JDBC));
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.payById(billPaymentOperation.getUserId(), billPaymentOperation.getAmount());
        if (userAccount != null && userAccount.getValidity().getTime() > System.currentTimeMillis()) {
            boolean updated = userAccountService.updateBalanceById(userAccount.getBalance(), billPaymentOperation.getUserId());
            boolean added = billPaymentService.add(billPaymentOperation);
            if (updated && added) {
                TransactionManager.commitTransaction(connection);
                return true;
            }
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }

    public UserAccount getUserAccount(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return userAccount;
    }
}
