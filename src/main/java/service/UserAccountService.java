package service;

import dao.intefaces.UserAccountDAO;
import model.UserAccount;

import java.util.List;

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

    public List<UserAccount> findAll() {
        return userAccountDAO.findAll();
    }

    public boolean updateDepositStatusById(int id) {
        return userAccountDAO.updateDepositStatusById(id);
    }

    public boolean updateCreditStatusById(int id) {
        return userAccountDAO.updateCreditStatusById(id);
    }
}
