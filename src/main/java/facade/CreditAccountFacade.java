package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.CreditAccount;
import model.UserAccount;
import model.calculation.CreditCalculator;
import service.CreditAccountService;
import service.UserAccountService;
import util.ConnectionClosure;

import java.sql.Connection;
import java.util.List;

import static enums.DAOEnum.CREDIT_ACCOUNT_JDBC;
import static enums.DAOEnum.USER_ACCOUNT_JDBC;

/**
 * A class that works with CreditAccountService, UserAccountService.
 *
 * @see CreditAccountService
 * @see UserAccountService
 */
public class CreditAccountFacade {

    private CreditAccountService creditAccountService;
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
    public CreditAccountFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
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
     * Method to set UserAccountService object {@link #userAccountService}.
     *
     * @param userAccountService The UserAccountService object.
     * @see UserAccountService
     */
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    /**
     * Method to get CreditAccount object by user id.
     *
     * @param userId The user id.
     * @return The CreditAccount object.
     * @see CreditAccount
     */
    public CreditAccount getCreditAccount(int userId) {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        CreditAccount creditAccount = creditAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return creditAccount;
    }

    /**
     * Method to get all credit accounts.
     *
     * @return The list of CreditAccount objects.
     * @see CreditAccount
     */
    public List<CreditAccount> getAll() {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        List<CreditAccount> list = creditAccountService.getAll();
        ConnectionClosure.close(connection);
        return list;
    }

    /**
     * Method to check arrears by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if user has arrears; <code>false</code> otherwise.
     */
    public boolean checkArrears(int userId) {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        if (creditAccountService.getById(userId).getArrears() <= 0) {
            ConnectionClosure.close(connection);
            return false;
        }
        ConnectionClosure.close(connection);
        return true;
    }

    /**
     * Method to update interest charges using CreditAccount object.
     *
     * @param creditAccount The CreditAccount object.
     * @return <code>true</code> if interest charges were updated; <code>false</code> otherwise.
     * @see CreditAccount
     */
    public boolean updateInterestCharges(CreditAccount creditAccount) {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        CreditCalculator creditCalculator = new CreditCalculator(creditAccount);
        double amount = creditCalculator.calculate();
        boolean updated = creditAccountService.updateInterestCharges(creditAccount.getInterestCharges() + amount, creditAccount.getUserId());
        ConnectionClosure.close(connection);
        return updated;
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
