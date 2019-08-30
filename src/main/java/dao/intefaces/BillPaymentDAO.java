package dao.intefaces;

import model.BillPaymentOperation;

import java.util.List;

public interface BillPaymentDAO extends DAO<BillPaymentOperation> {
    List<BillPaymentOperation> getAllById(int id);
}
