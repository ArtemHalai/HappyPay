package service;

import dao.intefaces.CreditApprovementDAO;
import model.CreditApprovementOperation;
import model.CreditRequest;
import model.CreditRequestAdmin;

import java.util.List;

/**
 * A class that works with CreditApprovementDAO.
 *
 * @see CreditApprovementDAO
 */
public class CreditApprovementService {

    private CreditApprovementDAO creditApprovementDAO;

    /**
     * Method to set CreditApprovementDAO object {@link #creditApprovementDAO}.
     *
     * @param creditApprovementDAO The CreditApprovementDAO object.
     * @see CreditApprovementDAO
     */
    public void setCreditApprovementDAO(CreditApprovementDAO creditApprovementDAO) {
        this.creditApprovementDAO = creditApprovementDAO;
    }

    /**
     * Method to create credit request using CreditRequest object.
     *
     * @param request The CreditRequest object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see CreditRequest
     */
    public boolean createCreditRequest(CreditRequest request) {
        return creditApprovementDAO.createCreditRequest(request);
    }

    /**
     * Method to get CreditApprovementOperation object by user id.
     *
     * @param id The user id.
     * @return The CreditApprovementOperation object.
     * @see CreditApprovementOperation
     */
    public CreditApprovementOperation getById(int id) {
        return creditApprovementDAO.getById(id);
    }

    /**
     * Method to get all CreditRequestAdmin objects by given decision.
     *
     * @param decision The decision.
     * @return The list of CreditRequestAdmin objects.
     * @see CreditRequestAdmin
     */
    public List<CreditRequestAdmin> findAllByDecision(boolean decision) {
        return creditApprovementDAO.findAllByDecision(decision);
    }

    /**
     * Method to update decision by user id.
     *
     * @param decision The decision.
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateDecision(boolean decision, int userId) {
        return creditApprovementDAO.updateDecision(decision, userId);
    }

    /**
     * Method to delete request by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean deleteRequest(int userId){
        return creditApprovementDAO.deleteRequest(userId);
    }
}
