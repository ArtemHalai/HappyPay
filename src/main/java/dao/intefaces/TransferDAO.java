package dao.intefaces;

import model.TransferOperation;

import java.util.List;

public interface TransferDAO extends DAO<TransferOperation> {

    boolean add(TransferOperation transferOperation);

    List<TransferOperation> getAllById(int id);
}
