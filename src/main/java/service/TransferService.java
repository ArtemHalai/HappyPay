package service;

import dao.intefaces.TransferDAO;
import factories.DaoFactory;
import model.AllOperationsDTO;
import model.TransferOperation;

import java.sql.Connection;

import static enums.DAOEnum.TRANSFER_JDBC;

public class TransferService {

    private TransferDAO transferDAO;
    private DaoFactory factory;

    public TransferService() {
        factory = DaoFactory.getInstance();
    }

    public void setTransferDAO(TransferDAO transferDAO) {
        this.transferDAO = transferDAO;
    }

    public void setDefaultTransferDAO(Connection connection) {
        this.transferDAO = factory.getTransferDAO(connection, TRANSFER_JDBC);
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
