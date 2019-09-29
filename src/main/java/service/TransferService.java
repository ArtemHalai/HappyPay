package service;

import dao.intefaces.TransferDAO;
import model.AllOperationsDTO;
import model.TransferOperation;

public class TransferService {

    private TransferDAO transferDAO;

    public void setTransferDAO(TransferDAO transferDAO) {
        this.transferDAO = transferDAO;
    }

    public boolean add(TransferOperation transferOperation) {
        return transferDAO.add(transferOperation);
    }

    public TransferOperation getById(int id) {
        return transferDAO.getById(id);
    }

    public AllOperationsDTO getAllOperations(AllOperationsDTO allOperationsDTO) {
        return transferDAO.getLimitOperations(allOperationsDTO);
    }
}
