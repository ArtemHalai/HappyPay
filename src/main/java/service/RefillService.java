package service;

import dao.intefaces.RefillDAO;
import model.RefillOperation;

import java.util.List;

public class RefillService {

    private RefillDAO refillDAO;

    public void setRefillDAO(RefillDAO refillDAO) {
        this.refillDAO = refillDAO;
    }

    public List<RefillOperation> getAllById(int id) {
        return refillDAO.getAllById(id);
    }

    public boolean add(RefillOperation refillOperation) {
        return refillDAO.add(refillOperation);
    }

    public RefillOperation getById(int id) {
        return refillDAO.getById(id);
    }

    public List<RefillOperation> findAll() {
        return refillDAO.findAll();
    }
}
