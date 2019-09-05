package service;

import dao.intefaces.TransferDAO;
import model.TransferOperation;

import java.util.List;

public class TransferService {

    private TransferDAO transferDAO;

    public void setTransferDAO(TransferDAO transferDAO) {
        this.transferDAO = transferDAO;
    }

    public List<TransferOperation> getAllById(int id) {
        return transferDAO.getAllById(id);
    }

    public boolean add(TransferOperation transferOperation) {
        return transferDAO.add(transferOperation);
    }

    public TransferOperation getById(int id) {
        return transferDAO.getById(id);
    }

    public List<TransferOperation> findAll() {
        return transferDAO.findAll();
    }
}
