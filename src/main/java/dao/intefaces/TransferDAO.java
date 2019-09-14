package dao.intefaces;

import model.AllOperationsDTO;
import model.TransferOperation;

import java.util.List;

public interface TransferDAO extends DAO<TransferOperation> {

    boolean add(TransferOperation transferOperation);

    AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO);

    int count(int userId);
}
