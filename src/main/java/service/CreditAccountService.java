package service;

import dao.intefaces.CreditAccountDAO;
import model.CreditAccount;

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

    public CreditAccount getById(int id) {
        return creditAccountDAO.getById(id);
    }

    public List<CreditAccount> findAll() {
        return creditAccountDAO.findAll();
    }
}
