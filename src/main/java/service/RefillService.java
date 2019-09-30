package service;

import dao.intefaces.RefillDAO;
import model.AllOperationsDTO;
import model.RefillOperation;
import model.RefillPaginationDTO;

/**
 * A class that works with RefillDAO.
 *
 * @see RefillDAO
 */
public class RefillService {

    private RefillDAO refillDAO;

    /**
     * Method to set RefillDAO object {@link #refillDAO}.
     *
     * @param refillDAO The RefillDAO object.
     * @see RefillDAO
     */
    public void setRefillDAO(RefillDAO refillDAO) {
        this.refillDAO = refillDAO;
    }

    /**
     * Method to add RefillOperation object.
     *
     * @param refillOperation The RefillOperation object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see RefillOperation
     */
    public boolean add(RefillOperation refillOperation) {
        return refillDAO.add(refillOperation);
    }

    /**
     * Method to get RefillOperation object by user id.
     *
     * @param id The user id.
     * @return The RefillOperation object.
     * @see RefillOperation
     */
    public RefillOperation getById(int id) {
        return refillDAO.getById(id);
    }

    /**
     * Method to get all refill operations.
     *
     * @param paginationDTO The RefillPaginationDTO object.
     * @return The RefillPaginationDTO object containing necessary data for pagination with refill operations.
     * @see RefillPaginationDTO
     */
    public RefillPaginationDTO getRefillOperations(RefillPaginationDTO paginationDTO) {
        return refillDAO.getRefillOperations(paginationDTO);
    }

    /**
     * Method to get AllOperationsDTO object using AllOperationsDTO object.
     *
     * @param allOperationsDTO The AllOperationsDTO object.
     * @return The AllOperationsDTO object containing necessary data of all operations.
     * @see AllOperationsDTO
     */
    public AllOperationsDTO getAllOperations(AllOperationsDTO allOperationsDTO) {
        return refillDAO.getLimitOperations(allOperationsDTO);
    }
}
