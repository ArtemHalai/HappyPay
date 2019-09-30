package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.ClientDetails;
import model.User;
import model.UserAccount;
import service.*;
import util.DateValidity;
import util.TransactionManager;

import java.sql.Connection;
import static enums.DAOEnum.*;

/**
 * A class that works with ClientDetailsService, UserService, UserAccountService.
 *
 * @see ClientDetailsService
 * @see UserService
 * @see UserAccountService
 */
public class RegistrationFacade {

    private ClientDetailsService clientDetailsService;
    private UserService userService;
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
    public RegistrationFacade() {
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
     * Method to set ClientDetailsService object {@link #clientDetailsService}.
     *
     * @param clientDetailsService The ClientDetailsService object.
     * @see ClientDetailsService
     */
    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    /**
     * Method to set UserService object {@link #userService}.
     *
     * @param userService The UserService object.
     * @see UserService
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method to add user using ClientDetails object.
     *
     * @param clientDetails The ClientDetails object.
     * @return The int value representing auto-generated id in database.
     * @see ClientDetails
     * {@link #addNewUser(ClientDetails)}
     */
    public int addUser(ClientDetails clientDetails) {
        int userId = -1;
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        userService.setUserDAO(factory.getUserDAO(connection, USER_JDBC));
        clientDetailsService.setClientDetailsDAO(factory.getClientDetailsDAO(connection, CLIENT_DETAILS_JDBC));
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        if (userService.isUserExist(clientDetails.getUsername())) {
            TransactionManager.rollbackTransaction(connection);
            return userId;
        } else {
            return addNewUser(clientDetails);
        }
    }

    /**
     * Method to add user using ClientDetails object.
     *
     * @param clientDetails The ClientDetails object.
     * @return The int value representing auto-generated id in database.
     * @see ClientDetails
     */
    private int addNewUser(ClientDetails clientDetails) {
        int userId;
        int unsuccessful = -1;
        User user = new User();
        user.setUsername(clientDetails.getUsername());
        user.setPassword(clientDetails.getPassword());
        userId = userService.addUser(user);
        clientDetails.setUserId(userId);
        boolean clientDetailsAdded = clientDetailsService.add(clientDetails);
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userId);
        userAccount.setValidity(DateValidity.setValidity());
        userAccount.setCredit(false);
        userAccount.setDeposit(false);
        boolean userAccountAdded = userAccountService.add(userAccount);
        if (userId > 0 && clientDetailsAdded && userAccountAdded) {
            TransactionManager.commitTransaction(connection);
            return userId;
        }
        TransactionManager.rollbackTransaction(connection);
        return unsuccessful;
    }
}
