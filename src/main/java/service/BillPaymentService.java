package service;

import dao.intefaces.BillPaymentDAO;
import factories.DaoFactory;
import model.AllOperationsDTO;
import model.BillPaymentOperation;

import java.sql.Connection;

import static enums.DAOEnum.BILL_PAYMENT_JDBC;

public class BillPaymentService {

    private BillPaymentDAO billPaymentDAO;
    private DaoFactory factory;

    public BillPaymentService() {
        factory = DaoFactory.getInstance();
    }

    public void setBillPaymentDAO(BillPaymentDAO billPaymentDAO) {
        this.billPaymentDAO = billPaymentDAO;
    }

    public void setDefaultBillPaymentDAO(Connection connection) {
        this.billPaymentDAO = factory.getBillPaymentDAO(connection, BILL_PAYMENT_JDBC);
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
