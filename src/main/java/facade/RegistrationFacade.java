package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.ClientDetails;
import model.User;
import service.ClientDetailsService;
import service.UserService;
import util.ConnectionClosure;

import java.sql.Connection;

import static enums.DAOEnum.CLIENT_DETAILS_JDBC;
import static enums.DAOEnum.USER_JDBC;

public class RegistrationFacade {

    private ClientDetailsService clientDetailsService;
    private UserService userService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public RegistrationFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
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

        userService.setUserDAO(factory.getUserDAO(connection, USER_JDBC));
        clientDetailsService.setClientDetailsDAO(factory.getClientDetailsDAO(connection, CLIENT_DETAILS_JDBC));
        if (userService.isUserExist(clientDetails.getUsername())) {
            ConnectionClosure.close(connection);
            return userId;
        } else {
            User user = new User();
            user.setUsername(clientDetails.getUsername());
            user.setPassword(clientDetails.getPassword());
            userId = userService.addUser(user);
            clientDetails.setUserId(userId);
            clientDetailsService.add(clientDetails);
            ConnectionClosure.close(connection);
            return userId;
        }
    }
}
