package service;

import dao.intefaces.LimitRequestDAO;
import model.LimitRequest;
import model.LimitRequestAdmin;

import java.util.List;

/**
 * A class that works with LimitRequestDAO.
 *
 * @see LimitRequestDAO
 */
public class LimitRequestService {

    private LimitRequestDAO limitRequestDAO;

    /**
     * Method to set LimitRequestDAO object {@link #limitRequestDAO}.
     *
     * @param limitRequestDAO The LimitRequestDAO object.
     * @see LimitRequestDAO
     */
    public void setLimitRequestDAO(LimitRequestDAO limitRequestDAO) {
        this.limitRequestDAO = limitRequestDAO;
    }

    /**
     * Method to add LimitRequest object.
     *
     * @param limitRequest The LimitRequest object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see LimitRequest
     */
    public boolean add(LimitRequest limitRequest) {
        return limitRequestDAO.add(limitRequest);
    }

    /**
     * Method to get all LimitRequestAdmin objects by given decision.
     *
     * @param decision The decision.
     * @return The list of LimitRequestAdmin objects.
     * @see LimitRequestAdmin
     */
    public List<LimitRequestAdmin> findAllByDecision(boolean decision) {
        return limitRequestDAO.findAllByDecision(decision);
    }

    /**
     * Method to update decision by user id.
     *
     * @param decision The decision.
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateDecision(boolean decision, int userId) {
        return limitRequestDAO.updateDecision(decision, userId);
    }

    /**
     * Method to delete request by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean deleteRequest(int userId) {
        return limitRequestDAO.deleteRequest(userId);
    }

    /**
     * Method to get LimitRequest object by user id.
     *
     * @param userId The user id.
     * @return The LimitRequest object.
     * @see LimitRequest
     */
    public LimitRequest getById(int userId) {
        return limitRequestDAO.getById(userId);
    }
}
