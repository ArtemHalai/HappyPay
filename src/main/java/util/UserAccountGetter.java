package util;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.UserAccount;
import service.UserAccountService;

import java.sql.Connection;
import java.sql.SQLException;

import static enums.DAOEnum.USER_ACCOUNT_JDBC;

@Log4j
public class UserAccountGetter {

    private static UserAccountService userAccountService;
    private static DaoFactory factory;
    private static JDBCConnectionFactory connectionFactory;

    static {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
        userAccountService = ServiceFactory.getInstance().getUserAccountService();
    }

    public static UserAccount getUserAccount(int userId) {
        UserAccount userAccount = new UserAccount();
        try (Connection connection = connectionFactory.getConnection()) {
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            userAccount = userAccountService.getById(userId);
        } catch (SQLException e) {
            log.error("Could not get user account", e);
        }
        return userAccount;
    }
}
