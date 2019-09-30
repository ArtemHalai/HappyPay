package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.UserAccount;
import service.UserAccountService;
import util.ConnectionClosure;

import java.sql.Connection;

import static enums.DAOEnum.USER_ACCOUNT_JDBC;

/**
 * A class that works with UserAccountService.
 *
 * @see UserAccountService
 */
public class UpdateTermFacade {

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
    public UpdateTermFacade() {
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
     * Method to update term of account.
     *
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateTerm(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        boolean updated = userAccountService.updateTerm(userId);
        if (updated) {
            ConnectionClosure.close(connection);
            return true;
        }
        ConnectionClosure.close(connection);
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
