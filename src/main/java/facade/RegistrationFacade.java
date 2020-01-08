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

public class RegistrationFacade {

    private ClientDetailsService clientDetailsService;
    private UserService userService;
    private UserAccountService userAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public RegistrationFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
        userAccount.setValidity(DateValidity.getValidity());
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
