package dao.intefaces;

import model.CreditApprovementOperation;
import model.CreditRequest;
import model.CreditRequestAdmin;

import java.util.List;

/**
 * Define a data access object used for executing credit approvement's requests to database.
 */
public interface CreditApprovementDAO extends DAO<CreditApprovementOperation> {

    /**
     * Method to create credit request.
     *
     * @param creditRequest The CreditRequest object.
     * @return <code>true</code> if credit request was created; <code>false</code> otherwise.
     * @see CreditRequest
     */
    boolean createCreditRequest(CreditRequest creditRequest);

    /**
     * Method to get all credit requests by decision.
     *
     * @param decision The boolean value representing decision of credit request.
     * @return The list containing all credit requests with given decision.
     * @see CreditRequestAdmin
     */
    List<CreditRequestAdmin> findAllByDecision(boolean decision);

    /**
     * Method to update decision of credit request by user id.
     *
     * @param decision The boolean value representing decision of credit request.
     * @param userId The user id.
     * @return <code>true</code> if decision was updated; <code>false</code> otherwise.
     */
    boolean updateDecision(boolean decision, int userId);

    /**
     * Method to delete credit request by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if request was deleted; <code>false</code> otherwise.
     */
    boolean deleteRequest(int userId);

    /**
     * Method to get count of all credit approvements in database by user id.
     *
     * @param userId The user id.
     * @return The int value representing amount of all credit approvements in database.
     */
    int count(int userId);
}
