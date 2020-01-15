package service;

import dao.intefaces.CreditApprovementDAO;
import factories.DaoFactory;
import model.CreditApprovementOperation;
import model.CreditRequest;
import model.CreditRequestAdmin;

import java.sql.Connection;
import java.util.List;

import static enums.DAOEnum.CREDIT_APPROVEMENT_JDBC;

public class CreditApprovementService {

    private CreditApprovementDAO creditApprovementDAO;
    private DaoFactory factory;

    public CreditApprovementService() {
        factory = DaoFactory.getInstance();
    }

    public void setCreditApprovementDAO(CreditApprovementDAO creditApprovementDAO) {
        this.creditApprovementDAO = creditApprovementDAO;
    }

    public void setDefaultCreditApprovementDAO(Connection connection) {
        this.creditApprovementDAO = factory.getCreditApprovementDAO(connection, CREDIT_APPROVEMENT_JDBC);
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
