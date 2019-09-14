package service;

import dao.intefaces.LimitRequestDAO;
import model.LimitRequest;
import model.LimitRequestAdmin;

import java.util.List;

public class LimitRequestService {

    private LimitRequestDAO limitRequestDAO;

    public void setLimitRequestDAO(LimitRequestDAO limitRequestDAO) {
        this.limitRequestDAO = limitRequestDAO;
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
