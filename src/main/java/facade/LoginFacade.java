package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.User;
import service.UserService;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class LoginFacade {

    private static final String ERROR = "Could not get user with username: %s";
    private UserService userService;
    private JDBCConnectionFactory connectionFactory;

    public LoginFacade() {
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User exist = null;
        try (Connection connection = connectionFactory.getConnection()) {
            userService.setDefaultUserDAO(connection);
            exist = userService.getUserByUsernameAndPassword(username, password);
        } catch (SQLException e) {
            log.error(String.format(ERROR, username), e);
        }
        return exist;
    }
}
