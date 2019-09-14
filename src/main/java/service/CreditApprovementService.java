package service;

import dao.intefaces.CreditApprovementDAO;
import model.AllOperationsDTO;
import model.CreditApprovementOperation;
import model.CreditRequest;
import model.CreditRequestAdmin;

import java.util.List;

public class CreditApprovementService {

    private CreditApprovementDAO creditApprovementDAO;

    public void setCreditApprovementDAO(CreditApprovementDAO creditApprovementDAO) {
        this.creditApprovementDAO = creditApprovementDAO;
    }

    public boolean createCreditRequest(CreditRequest request) {
        return creditApprovementDAO.createCreditRequest(request);
    }

    public CreditApprovementOperation getById(int id) {
        return creditApprovementDAO.getById(id);
    }

    public List<CreditRequestAdmin> findAllByDecision(boolean decision) {
        return creditApprovementDAO.findAllByDecision(decision);
    }

    public boolean updateDecision(boolean decision, int userId) {
        return creditApprovementDAO.updateDecision(decision, userId);
    }

    public boolean deleteRequest(int userId){
        return creditApprovementDAO.deleteRequest(userId);
    }
}
