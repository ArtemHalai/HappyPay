package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.CreditAccount;
import model.UserAccount;
import service.CreditAccountService;
import service.UserAccountService;
import util.ConnectionClosure;
import util.TransactionManager;

import java.sql.Connection;

import static enums.DAOEnum.CREDIT_ACCOUNT_JDBC;
import static enums.DAOEnum.USER_ACCOUNT_JDBC;

/**
 * A class that works with CreditAccountService, UserAccountService.
 *
 * @see CreditAccountService
 * @see UserAccountService
 */
public class PayInterestChargesFacade {

    private UserAccountService userAccountService;
    private CreditAccountService creditAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    /**
     * Sole constructor to initialize {@link #factory} and {@link #connectionFactory}.
     *
     * @see DaoFactory
     * @see JDBCConnectionFactory
     */
    public PayInterestChargesFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
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
     * Method to set CreditAccountService object {@link #creditAccountService}.
     *
     * @param creditAccountService The CreditAccountService object.
     * @see CreditAccountService
     */
    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
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

    /**
     * Method to check interest charges by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if user has interest charges; <code>false</code> otherwise.
     */
    public boolean checkInterestCharges(int userId) {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        if (creditAccountService.getById(userId).getInterestCharges() <= 0) {
            ConnectionClosure.close(connection);
            return false;
        }
        ConnectionClosure.close(connection);
        return true;
    }

    /**
     * Method to pay interest charges by user id and amount.
     *
     * @param userId The user id.
     * @param amount The amount.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean payInterestCharges(int userId, double amount) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.payById(userId, amount);
        if (userAccount != null && userAccount.getCredit()) {
            CreditAccount creditAccount = creditAccountService.getById(userId);

            if (creditAccount.getInterestCharges() < amount && creditAccountService.updateInterestCharges(0, userId)) {
                double returnAmount = amount - creditAccount.getInterestCharges();
                userAccountService.updateBalanceById(userAccount.getBalance() + returnAmount, userId);
                TransactionManager.commitTransaction(connection);
                return true;
            }

            if (creditAccount.getInterestCharges() >= amount && creditAccountService.updateInterestCharges(creditAccount.getInterestCharges() - amount, userId)) {
                userAccountService.updateBalanceById(userAccount.getBalance(), userId);
                TransactionManager.commitTransaction(connection);
                return true;
            }
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }
}
