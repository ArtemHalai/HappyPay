package service;

import dao.intefaces.DepositAccountDAO;
import model.DepositAccount;
import model.RefillOperation;

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

    public DepositAccount payById(int id, double amount) {
        DepositAccount depositAccount = depositAccountDAO.getById(id);
        if (depositAccount.getBalance() >= amount) {
            depositAccount.setBalance(depositAccount.getBalance() - amount);
            return depositAccount;
        }
        return null;
    }

    public DepositAccount getById(int id) {
        return depositAccountDAO.getById(id);
    }

    public boolean updateTerm(int userId) {
        DepositAccount depositAccount = depositAccountDAO.getById(userId);
        if (depositAccount.getTerm().getTime() > System.currentTimeMillis())
            return false;
        return depositAccountDAO.updateTerm(userId);
    }

    public boolean deleteDepositAccount(int userId) {
        return depositAccountDAO.deleteDepositAccount(userId);
    }

    public List<DepositAccount> getAll() {
        return depositAccountDAO.getAll();
    }
}
