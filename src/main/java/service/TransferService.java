package service;

import dao.intefaces.TransferDAO;
import model.AllOperationsDTO;
import model.TransferOperation;

/**
 * A class that works with TransferDAO.
 *
 * @see TransferDAO
 */
public class TransferService {

    private TransferDAO transferDAO;

    /**
     * Method to set TransferDAO object {@link #transferDAO}.
     *
     * @param transferDAO The TransferDAO object.
     * @see TransferDAO
     */
    public void setTransferDAO(TransferDAO transferDAO) {
        this.transferDAO = transferDAO;
    }

    /**
     * Method to add TransferOperation object.
     *
     * @param transferOperation The TransferOperation object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see TransferOperation
     */
    public boolean add(TransferOperation transferOperation) {
        return transferDAO.add(transferOperation);
    }

    /**
     * Method to get TransferOperation object by user id.
     *
     * @param id The user id.
     * @return The TransferOperation object.
     * @see TransferOperation
     */
    public TransferOperation getById(int id) {
        return transferDAO.getById(id);
    }

    /**
     * Method to get AllOperationsDTO object using AllOperationsDTO object.
     *
     * @param allOperationsDTO The AllOperationsDTO object.
     * @return The AllOperationsDTO object containing necessary data of all operations.
     * @see AllOperationsDTO
     */
    public AllOperationsDTO getAllOperations(AllOperationsDTO allOperationsDTO) {
        return transferDAO.getLimitOperations(allOperationsDTO);
    }
}
