package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.LimitRequest;
import model.UserAccount;
import service.LimitRequestService;
import util.UserAccountGetter;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class LimitRequestFacade {

    private static final String ERROR = "Could not add limit request for user with id: %d";
    private LimitRequestService limitRequestService;
    private JDBCConnectionFactory connectionFactory;

    public LimitRequestFacade() {
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setLimitRequestService(LimitRequestService limitRequestService) {
        this.limitRequestService = limitRequestService;
    }

    public boolean addLimitRequest(LimitRequest limitRequest) {
        try (Connection connection = connectionFactory.getConnection()) {
            limitRequestService.setDefaultLimitRequestDAO(connection);
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
