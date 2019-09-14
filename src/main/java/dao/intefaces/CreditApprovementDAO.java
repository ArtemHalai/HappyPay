package dao.intefaces;

import model.CreditApprovementOperation;
import model.CreditRequest;
import model.CreditRequestAdmin;

import java.util.List;

public interface CreditApprovementDAO extends DAO<CreditApprovementOperation> {

    boolean createCreditRequest(CreditRequest creditRequest);

    List<CreditRequestAdmin> findAllByDecision(boolean decision);

    boolean updateDecision(boolean decision, int userId);

    boolean deleteRequest(int userId);

    int count(int userId);
}
