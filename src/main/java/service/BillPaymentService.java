package service;

import dao.intefaces.BillPaymentDAO;
import model.AllOperationsDTO;
import model.BillPaymentOperation;

/**
 * A class that works with BillPaymentDAO.
 *
 * @see BillPaymentDAO
 */
public class BillPaymentService {

    private BillPaymentDAO billPaymentDAO;

    /**
     * Method to set BillPaymentDAO object {@link #billPaymentDAO}.
     *
     * @param billPaymentDAO The BillPaymentDAO object.
     * @see BillPaymentDAO
     */
    public void setBillPaymentDAO(BillPaymentDAO billPaymentDAO) {
        this.billPaymentDAO = billPaymentDAO;
    }

    /**
     * Method to add bill payment operation using BillPaymentOperation object.
     *
     * @param billPaymentOperation The BillPaymentOperation object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see BillPaymentOperation
     */
    public boolean add(BillPaymentOperation billPaymentOperation) {
        return billPaymentDAO.add(billPaymentOperation);
    }

    /**
     * Method to get BillPaymentOperation object by user id.
     *
     * @param id The user id.
     * @return The BillPaymentOperation object.
     * @see BillPaymentOperation
     */
    public BillPaymentOperation getById(int id) {
        return billPaymentDAO.getById(id);
    }

    /**
     * Method to get AllOperationsDTO object using AllOperationsDTO object.
     *
     * @param allOperationsDTO The AllOperationsDTO object.
     * @return The AllOperationsDTO object containing necessary data of all operations.
     * @see AllOperationsDTO
     */
    public AllOperationsDTO getAllOperations(AllOperationsDTO allOperationsDTO) {
        return billPaymentDAO.getLimitOperations(allOperationsDTO);
    }
}
