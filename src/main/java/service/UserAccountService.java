package service;

import dao.intefaces.UserAccountDAO;
import factories.DaoFactory;
import model.UserAccount;
import util.UserAccountValidity;

import java.sql.Connection;

import static enums.DAOEnum.USER_ACCOUNT_JDBC;

public class UserAccountService {

    private UserAccountDAO userAccountDAO;
    private DaoFactory factory;

    public UserAccountService() {
        factory = DaoFactory.getInstance();
    }

    public void setUserAccountDAO(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    public void setDefaultUserAccountDAO(Connection connection) {
        this.userAccountDAO = factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC);
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
        if (UserAccountValidity.userAccountIsValid(userAccount)) {
            return false;
        }
        return userAccountDAO.updateTerm(userId);
    }

    public UserAccount getByAccountNumber(long recipientAccountNumber) {
        return userAccountDAO.getByAccountNumber(recipientAccountNumber);
    }

    public boolean updateByAccount(double amount, long recipientAccountNumber) {
        return userAccountDAO.updateByAccount(amount, recipientAccountNumber);
    }

    public boolean updateDepositStatusById(int userId, boolean decision) {
        return userAccountDAO.updateDepositStatusById(userId, decision);
    }
}
