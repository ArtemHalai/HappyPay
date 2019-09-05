package service;

import dao.intefaces.CreditApprovementDAO;
import model.CreditApprovementOperation;
import model.CreditRequest;

import java.util.List;

public class CreditApprovementService {

    private CreditApprovementDAO creditApprovementDAO;

    public void setCreditApprovementDAO(CreditApprovementDAO creditApprovementDAO) {
        this.creditApprovementDAO = creditApprovementDAO;
    }

    public boolean add(CreditApprovementOperation creditApprovementOperation) {
        return creditApprovementDAO.add(creditApprovementOperation);
    }

    public boolean createCreditRequest(CreditRequest request) {
        return creditApprovementDAO.createCreditRequest(request);
    }

    public List<CreditApprovementOperation> getAllById(int id) {
        return creditApprovementDAO.getAllById(id);
    }

    public CreditApprovementOperation getById(int id) {
        return creditApprovementDAO.getById(id);
    }

    public List<CreditApprovementOperation> findAll() {
        return creditApprovementDAO.findAll();
    }
}
