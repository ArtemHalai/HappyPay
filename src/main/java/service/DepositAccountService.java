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

    public DepositAccount getByAccountNumber(long accountNumber) {
        return depositAccountDAO.isAccountNumberExist(accountNumber);
    }

    public boolean updateByAccount(double amount, long account) {
        return depositAccountDAO.updateBalanceByAccount(amount, account);
    }

    public List<DepositAccount> findAll() {
        return depositAccountDAO.findAll();
    }

    public DepositAccount getByAccountAndIban(RefillOperation refillOperation) {
        DepositAccount depositAccount = depositAccountDAO.getByAccountAndIban(refillOperation);
        if (depositAccount.getUserId() < 0)
            return null;
        if (depositAccount.getBalance() >= refillOperation.getAmount()) {
            depositAccount.setBalance(depositAccount.getBalance() - refillOperation.getAmount());
            return depositAccount;
        }
        return null;
    }
}
