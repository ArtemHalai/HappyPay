package dao.intefaces;

import model.AllOperationsDTO;
import model.TransferOperation;

/**
 * Define a data access object used for executing transfer requests to database.
 */
public interface TransferDAO extends DAO<TransferOperation> {

    /**
     * Method to add transfer operation.
     *
     * @param transferOperation The TransferOperation object.
     * @return <code>true</code> if transfer operation was added; <code>false</code> otherwise.
     * @see TransferOperation
     */
    boolean add(TransferOperation transferOperation);

    /**
     * Method to get limited list of transfer operations.
     *
     * @param allOperationsDTO The AllOperationsDTO object.
     * @return The AllOperationsDTO object containing data needed to collect different types of operations.
     * @see AllOperationsDTO
     */
    AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO);

    /**
     * Method to get count of all transfer operations in database by user id.
     *
     * @param userId The user id.
     * @return The int value representing amount of all transfer operations in database by given user id.
     */
    int count(int userId);
}
