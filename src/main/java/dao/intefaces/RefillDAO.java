package dao.intefaces;

import model.AllOperationsDTO;
import model.RefillOperation;
import model.RefillPaginationDTO;


public interface RefillDAO extends DAO<RefillOperation> {

    boolean add(RefillOperation refillOperation);

    RefillPaginationDTO getRefillOperations(RefillPaginationDTO paginationDTO);

    int count(int userId);

    AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO);
}
