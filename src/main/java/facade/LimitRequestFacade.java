package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.LimitRequest;
import model.UserAccount;
import service.LimitRequestService;
import util.UserAccountGetter;

import java.sql.Connection;
import java.sql.SQLException;

import static enums.DAOEnum.LIMIT_JDBC;

@Log4j
public class LimitRequestFacade {

    private static final String ERROR = "Could not add limit request for user with id: %d";
    private LimitRequestService limitRequestService;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public LimitRequestFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setLimitRequestService(LimitRequestService limitRequestService) {
        this.limitRequestService = limitRequestService;
    }

    public boolean addLimitRequest(LimitRequest limitRequest) {
        try (Connection connection = connectionFactory.getConnection()) {
            limitRequestService.setLimitRequestDAO(factory.getLimitRequestDAO(connection, LIMIT_JDBC));
            LimitRequest limit = limitRequestService.getById(limitRequest.getUserId());
            if (limit.getUserId() < 0 && limitRequestService.add(limitRequest)) {
                return true;
            }
        } catch (SQLException e) {
            log.error(String.format(ERROR, limitRequest.getUserId()), e);
        }
        return false;
    }

    public UserAccount getUserAccount(int userId) {
        return UserAccountGetter.getUserAccount(userId);
    }
}
