package dao.intefaces;

import model.RefillOperation;

import java.util.List;

public interface RefillDAO extends DAO<RefillOperation> {
    List<RefillOperation> getAllById(int id);
}
