package service;

import dao.intefaces.BillPaymentDAO;
import model.BillPaymentOperation;

import java.util.List;

public class BillPaymentService {

    private BillPaymentDAO billPaymentDAO;

    public void setBillPaymentDAO(BillPaymentDAO billPaymentDAO) {
        this.billPaymentDAO = billPaymentDAO;
    }

    public List<BillPaymentOperation> getAllById(int id) {
        return billPaymentDAO.getAllById(id);
    }

    public boolean add(BillPaymentOperation billPaymentOperation) {
        return billPaymentDAO.add(billPaymentOperation);
    }

    public BillPaymentOperation getById(int id) {
        return billPaymentDAO.getById(id);
    }

    public List<BillPaymentOperation> findAll() {
        return billPaymentDAO.findAll();
    }
}
