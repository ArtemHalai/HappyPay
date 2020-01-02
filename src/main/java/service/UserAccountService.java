package service;

import dao.intefaces.UserAccountDAO;
import model.UserAccount;

public class UserAccountService {

    private UserAccountDAO userAccountDAO;

    public void setUserAccountDAO(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    public boolean add(UserAccount userAccount) {
        return userAccountDAO.add(userAccount);
    }

    public UserAccount getById(int id) {
        return userAccountDAO.getById(id);
    }

    public boolean updateBalanceById(double amount, int userId) {
        return userAccountDAO.updateBalanceById(amount, userId);
    }

    public boolean updateCreditStatusById(int id, boolean decision) {
        return userAccountDAO.updateCreditStatusById(id, decision);
    }

    public boolean updateTerm(int userId) {
        UserAccount userAccount = userAccountDAO.getById(userId);
        if (userAccount.getValidity().getTime() > System.currentTimeMillis()) {
            return false;
        }
        return userAccountDAO.updateTerm(userId);
    }

    public UserAccount getByAccountNumber(long recipientAccountNumber) {
        return userAccountDAO.getByAccountNumber(recipientAccountNumber);
    }

    public UserAccount payById(int userId, double amount) {
        UserAccount userAccount = userAccountDAO.getById(userId);
        if (userAccount.getBalance() >= amount) {
            userAccount.setBalance(userAccount.getBalance() - amount);
            return userAccount;
        }
        return null;
    }

    public boolean updateByAccount(double amount, long recipientAccountNumber) {
        return userAccountDAO.updateByAccount(amount, recipientAccountNumber);
    }

    public boolean updateDepositStatusById(int userId, boolean decision) {
        return userAccountDAO.updateDepositStatusById(userId, decision);
    }
}
