package dao.intefaces;

import model.AllOperationsDTO;
import model.TransferOperation;

public interface TransferDAO extends DAO<TransferOperation> {

    boolean add(TransferOperation transferOperation);

    AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO);

    int count(int userId);
}
