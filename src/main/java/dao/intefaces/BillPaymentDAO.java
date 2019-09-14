package dao.intefaces;

import model.AllOperationsDTO;
import model.BillPaymentOperation;

import java.util.List;

public interface BillPaymentDAO extends DAO<BillPaymentOperation> {

    boolean add(BillPaymentOperation billPaymentOperation);

    AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO);

    int count(int userId);
}
