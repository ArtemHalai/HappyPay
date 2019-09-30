package dao.intefaces;

import model.LimitRequest;
import model.LimitRequestAdmin;

import java.util.List;

/**
 * Define a data access object used for executing limit requests to database.
 */
public interface LimitRequestDAO extends DAO<LimitRequest> {

    /**
     * Method to add limit request.
     *
     * @param limitRequest The LimitRequest object.
     * @return <code>true</code> if limit request was added; <code>false</code> otherwise.
     * @see LimitRequest
     */
    boolean add(LimitRequest limitRequest);

    /**
     * Method to get all limit requests by decision.
     *
     * @param decision The boolean value representing decision of limit request.
     * @return The list containing all limit requests with given decision.
     * @see LimitRequestAdmin
     */
    List<LimitRequestAdmin> findAllByDecision(boolean decision);

    /**
     * Method to update decision of limit request by user id.
     *
     * @param decision The boolean value representing decision of limit request.
     * @param userId The user id.
     * @return <code>true</code> if decision was updated; <code>false</code> otherwise.
     */
    boolean updateDecision(boolean decision, int userId);

    /**
     * Method to delete limit request by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if request was deleted; <code>false</code> otherwise.
     */
    boolean deleteRequest(int userId);
}
