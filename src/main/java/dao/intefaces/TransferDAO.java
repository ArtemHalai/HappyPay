package dao.intefaces;

import model.TransferOperation;

import java.util.List;

public interface TransferDAO extends DAO<TransferOperation> {
    List<TransferOperation> getAllById(int id);
}
