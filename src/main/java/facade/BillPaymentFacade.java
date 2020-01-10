package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.BillPaymentOperation;
import model.UserAccount;
import service.BillPaymentService;
import service.UserAccountService;
import util.ConnectionClosure;
import util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

import static enums.DAOEnum.*;

@Log4j
public class BillPaymentFacade {

    private BillPaymentService billPaymentService;
    private UserAccountService userAccountService;
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
        try (Connection connection = connectionFactory.getConnection()) {
            TransactionManager.setRepeatableRead(connection);
            billPaymentService.setBillPaymentDAO(factory.getBillPaymentDAO(connection, BILL_PAYMENT_JDBC));
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            UserAccount userAccount = userAccountService.payById(billPaymentOperation.getUserId(), billPaymentOperation.getAmount());
            if (userAccount != null && userAccount.getValidity().getTime() > System.currentTimeMillis()) {
                boolean updated = userAccountService.updateBalanceById(userAccount.getBalance(), billPaymentOperation.getUserId());
                boolean added = billPaymentService.add(billPaymentOperation);
                if (updated && added) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error("Could not make bill payment", e);
        }
        return false;
    }

    public UserAccount getUserAccount(int userId) {
        UserAccount userAccount = new UserAccount();
        try (Connection connection = connectionFactory.getConnection()) {
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            userAccount = userAccountService.getById(userId);
        } catch (SQLException e) {
            log.error("Could not get user account", e);
        }
        return userAccount;
    }
}
