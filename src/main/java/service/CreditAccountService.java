package service;

import dao.intefaces.CreditAccountDAO;
import model.CreditAccount;
import model.RefillOperation;

import java.util.List;

public class CreditAccountService {

    private CreditAccountDAO creditAccountDAO;

    public void setCreditAccountDAO(CreditAccountDAO creditAccountDAO) {
        this.creditAccountDAO = creditAccountDAO;
    }

    public boolean add(CreditAccount creditAccount) {
        return creditAccountDAO.add(creditAccount);
    }

    public boolean updateBalanceById(double amount, int userId) {
        return creditAccountDAO.updateBalanceById(amount, userId);
    }

    public CreditAccount payById(int id, double amount) {
        CreditAccount creditAccount = creditAccountDAO.getById(id);
        if (creditAccount.getBalance() >= amount) {
            creditAccount.setBalance(creditAccount.getBalance() - amount);
            return creditAccount;
        }
        return null;
    }

    public CreditAccount getById(int id) {
        return creditAccountDAO.getById(id);
    }

    public CreditAccount getByAccountNumber(long accountNumber) {
        return creditAccountDAO.isAccountNumberExist(accountNumber);
    }

    public boolean updateByAccount(double amount, long account) {
        return creditAccountDAO.updateBalanceByAccount(amount, account);
    }

    public List<CreditAccount> findAll() {
        return creditAccountDAO.findAll();
    }

    public CreditAccount getByAccountAndIban(RefillOperation refillOperation) {
        CreditAccount creditAccount = creditAccountDAO.getByAccountAndIban(refillOperation);
        if (creditAccount.getUserId() < 0)
            return null;
        if (creditAccount.getBalance() >= refillOperation.getAmount()) {
            creditAccount.setBalance(creditAccount.getBalance() - refillOperation.getAmount());
            return creditAccount;
        }
        return null;
    }
}
