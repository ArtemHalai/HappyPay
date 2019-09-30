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

/**
 * A class that works with LimitRequestService, UserAccountService.
 *
 * @see LimitRequestService
 * @see UserAccountService
 */
public class LimitRequestFacade {

    private LimitRequestService limitRequestService;
    private UserAccountService userAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    /**
     * Sole constructor to initialize {@link #factory} and {@link #connectionFactory}.
     *
     * @see DaoFactory
     * @see JDBCConnectionFactory
     */
    public LimitRequestFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    /**
     * Method to set UserAccountService object {@link #userAccountService}.
     *
     * @param userAccountService The UserAccountService object.
     * @see UserAccountService
     */
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    /**
     * Method to set LimitRequestService object {@link #limitRequestService}.
     *
     * @param limitRequestService The LimitRequestService object.
     * @see LimitRequestService
     */
    public void setLimitRequestService(LimitRequestService limitRequestService) {
        this.limitRequestService = limitRequestService;
    }

    /**
     * Method to add limit request using LimitRequest object.
     *
     * @param limitRequest The LimitRequest object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see LimitRequest
     */
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

    /**
     * Method to get UserAccount object by user id.
     *
     * @param userId The user id.
     * @return The UserAccount object.
     * @see UserAccount
     */
    public UserAccount getUserAccount(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return userAccount;
    }
}
