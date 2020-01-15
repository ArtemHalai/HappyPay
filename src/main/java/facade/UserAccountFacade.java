package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.UserAccount;
import service.UserAccountService;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class UserAccountFacade {

    private static final String ERROR = "Could not get user account for user with id: %d";
    private UserAccountService userAccountService;
    private JDBCConnectionFactory connectionFactory;

    public UserAccountFacade() {
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public UserAccount getUserAccount(int userId) {
        UserAccount userAccount = new UserAccount();
        try (Connection connection = connectionFactory.getConnection()) {
            userAccountService.setDefaultUserAccountDAO(connection);
            userAccount = userAccountService.getById(userId);
        } catch (SQLException e) {
            log.error(String.format(ERROR, userId), e);
        }
        return userAccount;
    }
}
