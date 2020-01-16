package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.LimitRequestAdmin;
import service.CreditAccountService;
import service.LimitRequestService;
import util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class LimitRequestAdminFacade {

    private LimitRequestService limitRequestService;
    private CreditAccountService creditAccountService;
    private JDBCConnectionFactory connectionFactory;

    public LimitRequestAdminFacade() {
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public void setLimitRequestService(LimitRequestService limitRequestService) {
        this.limitRequestService = limitRequestService;
    }

    public List<LimitRequestAdmin> findAllByDecision(boolean decision) {
        List<LimitRequestAdmin> list = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            limitRequestService.setDefaultLimitRequestDAO(connection);
            list = limitRequestService.findAllByDecision(decision);
        } catch (SQLException e) {
            log.error(String.format("Could not find all limit requests by decision: %s", decision), e);
        }
        return list;
    }

    public boolean updateLimit(int userId, double amount, boolean decision) {
        boolean updated;
        boolean updatedDecision;
        boolean deleted;
        try (Connection connection = connectionFactory.getConnection()) {
            TransactionManager.setRepeatableRead(connection);
            limitRequestService.setDefaultLimitRequestDAO(connection);
            creditAccountService.setDefaultCreditAccountDAO(connection);
            try {
                updated = creditAccountService.updateBalanceById(amount, userId);
                updatedDecision = limitRequestService.updateDecision(decision, userId);
                deleted = deleteRequest(userId);
            } catch (Exception e) {
                connection.rollback();
                log.error(String.format("Could not update limit for user with id: %d and decision: %s", userId, decision), e);
                return false;
            }
            if (updated && updatedDecision && deleted) {
                connection.commit();
                return true;
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error(String.format("Could not update limit for user with id: %d and decision: %s", userId, decision), e);
        }
        return false;
    }

    public boolean deleteRequest(int userId) {
        boolean deleted = false;
        try (Connection connection = connectionFactory.getConnection()) {
            limitRequestService.setDefaultLimitRequestDAO(connection);
            deleted = limitRequestService.deleteRequest(userId);
        } catch (SQLException e) {
            log.error(String.format("Could not delete limit request for user with id: %d", userId), e);
        }
        return deleted;
    }
}
