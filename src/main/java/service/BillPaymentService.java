package service;

import dao.intefaces.BillPaymentDAO;
import model.AllOperationsDTO;
import model.BillPaymentOperation;

import java.util.List;

public class BillPaymentService {

    private BillPaymentDAO billPaymentDAO;

    public void setBillPaymentDAO(BillPaymentDAO billPaymentDAO) {
        this.billPaymentDAO = billPaymentDAO;
    }

    public boolean add(BillPaymentOperation billPaymentOperation) {
        return billPaymentDAO.add(billPaymentOperation);
    }

    public BillPaymentOperation getById(int id) {
        return billPaymentDAO.getById(id);
    }

    public AllOperationsDTO getAllOperations(AllOperationsDTO allOperationsDTO) {
        return billPaymentDAO.getLimitOperations(allOperationsDTO);
    }
}
