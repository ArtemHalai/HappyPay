package service;

import dao.intefaces.DepositAccountDAO;
import factories.DaoFactory;
import model.DepositAccount;

import java.sql.Connection;
import java.util.List;

import static enums.DAOEnum.DEPOSIT_ACCOUNT_JDBC;

public class DepositAccountService {

    private DepositAccountDAO depositAccountDAO;
    private DaoFactory factory;

    public DepositAccountService() {
        factory = DaoFactory.getInstance();
    }

    public void setDepositAccountDAO(DepositAccountDAO depositAccountDAO) {
        this.depositAccountDAO = depositAccountDAO;
    }

    public void setDefaultDepositAccountDAO(Connection connection) {
        this.depositAccountDAO = factory.getDepositAccountDAO(connection, DEPOSIT_ACCOUNT_JDBC);
    }

    public boolean add(DepositAccount depositAccount) {
        return depositAccountDAO.add(depositAccount);
    }

    public boolean updateBalanceById(double amount, int userId) {
        return depositAccountDAO.updateBalanceById(amount, userId);
    }

    public DepositAccount getById(int id) {
        return depositAccountDAO.getById(id);
    }

    public boolean deleteDepositAccount(int userId) {
        return depositAccountDAO.deleteDepositAccount(userId);
    }

    public List<DepositAccount> getAll() {
        return depositAccountDAO.getAll();
    }
}
