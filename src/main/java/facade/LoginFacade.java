package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.User;
import service.UserService;
import util.ConnectionClosure;

import java.sql.Connection;

import static enums.DAOEnum.USER_JDBC;

/**
 * A class that works with UserService.
 *
 * @see UserService
 */
public class LoginFacade {

    private UserService userService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    /**
     * Sole constructor to initialize {@link #factory} and {@link #connectionFactory}.
     *
     * @see DaoFactory
     * @see JDBCConnectionFactory
     */
    public LoginFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
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
     * Method to get User object.
     *
     * @param user The User object.
     * @see User
     */
    public User getUserByUsernameAndPassword(User user) {
        connection = connectionFactory.getConnection();
        userService.setUserDAO(factory.getUserDAO(connection, USER_JDBC));
        User exist = userService.getUserByUsernameAndPassword(user);
        ConnectionClosure.close(connection);
        return exist;
    }
}
