package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.LimitRequest;
import model.UserAccount;
import service.LimitRequestService;
import service.UserAccountService;
import util.ConnectionClosure;

import java.sql.Connection;

import static enums.DAOEnum.LIMIT_JDBC;
import static enums.DAOEnum.USER_ACCOUNT_JDBC;

public class LimitRequestFacade {

    private LimitRequestService limitRequestService;
    private UserAccountService userAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public LimitRequestFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setLimitRequestService(LimitRequestService limitRequestService) {
        this.limitRequestService = limitRequestService;
    }

    public boolean addLimitRequest(LimitRequest limitRequest) {
        connection = connectionFactory.getConnection();
        limitRequestService.setLimitRequestDAO(factory.getLimitRequestDAO(connection, LIMIT_JDBC));
        LimitRequest limit = limitRequestService.getById(limitRequest.getUserId());
        if (limit.getUserId() < 0 && limitRequestService.add(limitRequest)) {
            ConnectionClosure.close(connection);
            return true;
        }
        ConnectionClosure.close(connection);
        return false;
    }

    public UserAccount getUserAccount(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return userAccount;
    }
}
