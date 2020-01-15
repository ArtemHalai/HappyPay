package service;

import dao.intefaces.RefillDAO;
import factories.DaoFactory;
import model.AllOperationsDTO;
import model.RefillOperation;
import model.RefillPaginationDTO;

import java.sql.Connection;

import static enums.DAOEnum.REFILL_JDBC;

public class RefillService {

    private RefillDAO refillDAO;
    private DaoFactory factory;

    public RefillService() {
        factory = DaoFactory.getInstance();
    }

    public void setRefillDAO(RefillDAO refillDAO) {
        this.refillDAO = refillDAO;
    }

    public void setDefaultRefillDAO(Connection connection) {
        this.refillDAO = factory.getRefillDAO(connection, REFILL_JDBC);
    }

    public boolean add(RefillOperation refillOperation) {
        return refillDAO.add(refillOperation);
    }

    public RefillOperation getById(int id) {
        return refillDAO.getById(id);
    }

    public RefillPaginationDTO getRefillOperations(RefillPaginationDTO paginationDTO) {
        return refillDAO.getRefillOperations(paginationDTO);
    }

    public AllOperationsDTO getAllOperations(AllOperationsDTO allOperationsDTO) {
        return refillDAO.getLimitOperations(allOperationsDTO);
    }
}
