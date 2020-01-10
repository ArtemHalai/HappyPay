package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.ClientDetails;
import model.User;
import model.UserAccount;
import service.*;
import util.DateValidity;
import util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

import static enums.DAOEnum.*;

@Log4j
public class RegistrationFacade {

    private ClientDetailsService clientDetailsService;
    private UserService userService;
    private UserAccountService userAccountService;
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

    public int addClientDetails(ClientDetails clientDetails) {
        int userId = -1;
        try (Connection connection = connectionFactory.getConnection()) {
            TransactionManager.setRepeatableRead(connection);
            userService.setUserDAO(factory.getUserDAO(connection, USER_JDBC));
            clientDetailsService.setClientDetailsDAO(factory.getClientDetailsDAO(connection, CLIENT_DETAILS_JDBC));
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            if (userService.isUserExist(clientDetails.getUsername())) {
                connection.rollback();
            } else {
                return addNewUser(clientDetails, connection);
            }
        } catch (SQLException e) {
            log.error("Could not add ClientDetails object", e);
        }
        return userId;
    }

    private int addNewUser(ClientDetails clientDetails, Connection connection) throws SQLException {
        int userId;
        int unsuccessful = -1;

        try {
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
                connection.commit();
                return userId;
            }
        } catch (Exception e) {
            connection.rollback();
            log.error("Could not add ClientDetails object", e);
            return unsuccessful;
        }
        connection.rollback();
        return unsuccessful;
    }
}
