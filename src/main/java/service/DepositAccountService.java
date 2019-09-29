package service;

import dao.intefaces.DepositAccountDAO;
import model.DepositAccount;

import java.util.List;

public class DepositAccountService {

    private DepositAccountDAO depositAccountDAO;

    public void setDepositAccountDAO(DepositAccountDAO depositAccountDAO) {
        this.depositAccountDAO = depositAccountDAO;
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
