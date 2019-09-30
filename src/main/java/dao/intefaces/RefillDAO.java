package dao.intefaces;

import model.AllOperationsDTO;
import model.RefillOperation;
import model.RefillPaginationDTO;

/**
 * Define a data access object used for executing refill requests to database.
 */
public interface RefillDAO extends DAO<RefillOperation> {

    /**
     * Method to add refill operation.
     *
     * @param refillOperation The RefillOperation object.
     * @return <code>true</code> if refill operation was added; <code>false</code> otherwise.
     * @see RefillOperation
     */
    boolean add(RefillOperation refillOperation);

    /**
     * Method to get refill operations.
     *
     * @param paginationDTO The RefillPaginationDTO object.
     * @return The RefillPaginationDTO object containing data needed to get all refill operations part by part.
     * @see RefillPaginationDTO
     */
    RefillPaginationDTO getRefillOperations(RefillPaginationDTO paginationDTO);

    /**
     * Method to get count of all refill operations in database by user id.
     *
     * @param userId The user id.
     * @return The int value representing amount of all refill operations in database by given user id.
     */
    int count(int userId);

    /**
     * Method to get limited list of refill operations.
     *
     * @param allOperationsDTO The AllOperationsDTO object.
     * @return The AllOperationsDTO object containing data needed to collect different types of operations.
     * @see AllOperationsDTO
     */
    AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO);
}
