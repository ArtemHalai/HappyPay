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

/**
 * A class that works with BillPaymentService, UserAccountService.
 *
 * @see BillPaymentService
 * @see UserAccountService
 */
public class BillPaymentFacade {

    private BillPaymentService billPaymentService;
    private UserAccountService userAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    /**
     * Sole constructor to initialize {@link #factory} and {@link #connectionFactory}.
     *
     * @see DaoFactory
     * @see JDBCConnectionFactory
     */
    public BillPaymentFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    /**
     * Method to set BillPaymentService object {@link #billPaymentService}.
     *
     * @param billPaymentService The BillPaymentService object.
     * @see BillPaymentService
     */
    public void setBillPaymentService(BillPaymentService billPaymentService) {
        this.billPaymentService = billPaymentService;
    }

    /**
     * Method to set UserAccountService object {@link #userAccountService}.
     *
     * @param userAccountService The UserAccountService object.
     * @see UserAccountService
     */
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    /**
     * Method to pay bill using BillPaymentOperation object.
     *
     * @param billPaymentOperation The BillPaymentOperation object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see BillPaymentOperation
     */
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

    /**
     * Method to get UserAccount object by user id.
     *
     * @param userId The user id.
     * @return The UserAccount object.
     * @see UserAccount
     */
    public UserAccount getUserAccount(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return userAccount;
    }
}
