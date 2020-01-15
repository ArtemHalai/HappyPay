package service;

import dao.intefaces.LimitRequestDAO;
import factories.DaoFactory;
import model.LimitRequest;
import model.LimitRequestAdmin;

import java.sql.Connection;
import java.util.List;

import static enums.DAOEnum.LIMIT_JDBC;

public class LimitRequestService {

    private LimitRequestDAO limitRequestDAO;
    private DaoFactory factory;

    public LimitRequestService() {
        factory = DaoFactory.getInstance();
    }

    public void setLimitRequestDAO(LimitRequestDAO limitRequestDAO) {
        this.limitRequestDAO = limitRequestDAO;
    }

    public void setDefaultLimitRequestDAO(Connection connection) {
        this.limitRequestDAO = factory.getLimitRequestDAO(connection, LIMIT_JDBC);
    }

    public boolean add(LimitRequest limitRequest) {
        return limitRequestDAO.add(limitRequest);
    }

    public List<LimitRequestAdmin> findAllByDecision(boolean decision) {
        return limitRequestDAO.findAllByDecision(decision);
    }

    public boolean updateDecision(boolean decision, int userId) {
        return limitRequestDAO.updateDecision(decision, userId);
    }

    public boolean deleteRequest(int userId) {
        return limitRequestDAO.deleteRequest(userId);
    }

    public LimitRequest getById(int userId) {
        return limitRequestDAO.getById(userId);
    }
}
