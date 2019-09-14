package dao.intefaces;

import model.LimitRequest;
import model.LimitRequestAdmin;

import java.util.List;

public interface LimitRequestDAO extends DAO<LimitRequest> {

    boolean add(LimitRequest limitRequest);

    List<LimitRequestAdmin> findAllByDecision(boolean decision);

    boolean updateDecision(boolean decision, int userId);

    boolean deleteRequest(int userId);
}
