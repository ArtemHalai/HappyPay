package util;

import factories.JDBCConnectionFactory;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.UserAccount;
import service.UserAccountService;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class UserAccountGetter {

    private static UserAccountService userAccountService;
    private static JDBCConnectionFactory connectionFactory;

    static {
        connectionFactory = JDBCConnectionFactory.getInstance();
        userAccountService = ServiceFactory.getInstance().getUserAccountService();
    }

    public static UserAccount getUserAccount(int userId) {
        UserAccount userAccount = new UserAccount();
        try (Connection connection = connectionFactory.getConnection()) {
            userAccountService.setDefaultUserAccountDAO(connection);
            userAccount = userAccountService.getById(userId);
        } catch (SQLException e) {
            log.error("Could not get user account", e);
        }
        return userAccount;
    }
}
