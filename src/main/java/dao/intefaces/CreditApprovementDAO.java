package dao.intefaces;

import model.CreditApprovementOperation;
import model.CreditRequest;

import java.util.List;

public interface CreditApprovementDAO extends DAO<CreditApprovementOperation> {

    boolean createCreditRequest(CreditRequest creditRequest);

    List<CreditApprovementOperation> getAllById(int id);
}