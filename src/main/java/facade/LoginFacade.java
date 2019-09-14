package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.User;
import service.UserService;
import util.ConnectionClosure;

import java.sql.Connection;

import static enums.DAOEnum.USER_JDBC;

public class LoginFacade {

    private UserService userService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public LoginFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User getUserByUsernameAndPassword(User user) {
        connection = connectionFactory.getConnection();
        userService.setUserDAO(factory.getUserDAO(connection, USER_JDBC));
        User exist = userService.getUserByUsernameAndPassword(user);
        ConnectionClosure.close(connection);
        return exist;
    }
}
