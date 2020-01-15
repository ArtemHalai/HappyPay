package facade;

import factories.JDBCConnectionFactory;
import model.LimitRequestAdmin;
import service.CreditAccountService;
import service.LimitRequestService;
import util.ConnectionClosure;

import java.sql.Connection;
import java.util.List;

public class LimitRequestAdminFacade {

    private LimitRequestService limitRequestService;
    private CreditAccountService creditAccountService;
    private Connection connection;
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
        connection = connectionFactory.getConnection();
        limitRequestService.setDefaultLimitRequestDAO(connection);
        List<LimitRequestAdmin> list = limitRequestService.findAllByDecision(decision);
        ConnectionClosure.close(connection);
        return list;
    }

    public boolean updateLimit(int userId, double amount, boolean decision) {
        connection = connectionFactory.getConnection();
        limitRequestService.setDefaultLimitRequestDAO(connection);
        creditAccountService.setDefaultCreditAccountDAO(connection);
        boolean updated = creditAccountService.updateBalanceById(amount, userId);
        boolean updatedDecision = limitRequestService.updateDecision(decision, userId);
        deleteRequest(userId);
        ConnectionClosure.close(connection);
        return updated && updatedDecision;
    }

    public boolean deleteRequest(int userId) {
        connection = connectionFactory.getConnection();
        limitRequestService.setDefaultLimitRequestDAO(connection);
        boolean deleted = limitRequestService.deleteRequest(userId);
        ConnectionClosure.close(connection);
        return deleted;
    }
}
