package service;

import dao.intefaces.RefillDAO;
import model.AllOperationsDTO;
import model.RefillOperation;
import model.RefillPaginationDTO;

import java.util.List;

public class RefillService {

    private RefillDAO refillDAO;

    public void setRefillDAO(RefillDAO refillDAO) {
        this.refillDAO = refillDAO;
    }

    public boolean add(RefillOperation refillOperation) {
        return refillDAO.add(refillOperation);
    }

    public RefillOperation getById(int id) {
        return refillDAO.getById(id);
    }

    public RefillPaginationDTO getRefillOperations(RefillPaginationDTO paginationDTO) {
        return refillDAO.getRefillOperations(paginationDTO);
    }

    public AllOperationsDTO getAllOperations(AllOperationsDTO allOperationsDTO) {
        return refillDAO.getLimitOperations(allOperationsDTO);
    }
}
