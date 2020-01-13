package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.LimitRequestAdmin;
import service.CreditAccountService;
import service.LimitRequestService;
import util.ConnectionClosure;

import java.sql.Connection;
import java.util.List;

import static enums.DAOEnum.CREDIT_ACCOUNT_JDBC;
import static enums.DAOEnum.LIMIT_JDBC;

public class LimitRequestAdminFacade {

    private LimitRequestService limitRequestService;
    private CreditAccountService creditAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public LimitRequestAdminFacade() {
        factory = DaoFactory.getInstance();
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
        limitRequestService.setLimitRequestDAO(factory.getLimitRequestDAO(connection, LIMIT_JDBC));
        List<LimitRequestAdmin> list = limitRequestService.findAllByDecision(decision);
        ConnectionClosure.close(connection);
        return list;
    }

    public boolean updateLimit(int userId, double amount, boolean decision) {
        connection = connectionFactory.getConnection();
        limitRequestService.setLimitRequestDAO(factory.getLimitRequestDAO(connection, LIMIT_JDBC));
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        boolean updated = creditAccountService.updateBalanceById(amount, userId);
        boolean updatedDecision = limitRequestService.updateDecision(decision, userId);
        deleteRequest(userId);
        ConnectionClosure.close(connection);
        return updated && updatedDecision;
    }

    public boolean deleteRequest(int userId) {
        connection = connectionFactory.getConnection();
        limitRequestService.setLimitRequestDAO(factory.getLimitRequestDAO(connection, LIMIT_JDBC));
        boolean deleted = limitRequestService.deleteRequest(userId);
        ConnectionClosure.close(connection);
        return deleted;
    }
}
