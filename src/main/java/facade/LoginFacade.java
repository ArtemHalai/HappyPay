package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.User;
import service.UserService;

import java.sql.Connection;
import java.sql.SQLException;

import static enums.DAOEnum.USER_JDBC;

@Log4j
public class LoginFacade {

    private UserService userService;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public LoginFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User exist = null;
        try (Connection connection = connectionFactory.getConnection()) {
            userService.setUserDAO(factory.getUserDAO(connection, USER_JDBC));
            exist = userService.getUserByUsernameAndPassword(username, password);
        } catch (SQLException e) {
            log.error("Could not get user", e);
        }
        return exist;
    }
}
