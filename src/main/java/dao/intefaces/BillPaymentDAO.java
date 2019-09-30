package dao.intefaces;

import model.AllOperationsDTO;
import model.BillPaymentOperation;

/**
 * Define a data access object used for executing bill payment's requests to database.
 */
public interface BillPaymentDAO extends DAO<BillPaymentOperation> {

    /**
     * Method to add bill payment.
     *
     * @param billPaymentOperation The BillPaymentOperation object.
     * @return <code>true</code> if bill payment operation was added; <code>false</code> otherwise.
     * @see BillPaymentOperation
     */
    boolean add(BillPaymentOperation billPaymentOperation);

    /**
     * Method to get limited list of bill payment operations.
     *
     * @param allOperationsDTO The AllOperationsDTO object.
     * @return The AllOperationsDTO object containing data needed to collect different types of operations.
     * @see AllOperationsDTO
     */
    AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO);

    /**
     * Method to get count of all bill payments in database by user id.
     *
     * @param userId The user id.
     * @return The int value representing amount of all bill payments in database by given user id.
     */
    int count(int userId);
}
