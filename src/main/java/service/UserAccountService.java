package service;

import dao.intefaces.UserAccountDAO;
import model.UserAccount;

/**
 * A class that works with UserAccountDAO.
 *
 * @see UserAccountDAO
 */
public class UserAccountService {

    private UserAccountDAO userAccountDAO;

    /**
     * Method to set UserAccountDAO object {@link #userAccountDAO}.
     *
     * @param userAccountDAO The UserAccountDAO object.
     * @see UserAccountDAO
     */
    public void setUserAccountDAO(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    /**
     * Method to add UserAccount object.
     *
     * @param userAccount The UserAccount object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see UserAccount
     */
    public boolean add(UserAccount userAccount) {
        return userAccountDAO.add(userAccount);
    }

    /**
     * Method to get UserAccount object by user id.
     *
     * @param id The user id.
     * @return The UserAccount object.
     * @see UserAccount
     */
    public UserAccount getById(int id) {
        return userAccountDAO.getById(id);
    }

    /**
     * Method to update balance by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateBalanceById(double amount, int userId){
        return userAccountDAO.updateBalanceById(amount, userId);
    }

    /**
     * Method to update credit status by user id.
     *
     * @param decision The amount.
     * @param id The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateCreditStatusById(int id, boolean decision) {
        return userAccountDAO.updateCreditStatusById(id, decision);
    }

    /**
     * Method to update term by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateTerm(int userId) {
        UserAccount userAccount = userAccountDAO.getById(userId);
        if (userAccount.getValidity().getTime() > System.currentTimeMillis())
            return false;
        return userAccountDAO.updateTerm(userId);
    }

    /**
     * Method to get UserAccount by account number.
     *
     * @param recipientAccountNumber The account number of recipient.
     * @return The UserAccount object.
     */
    public UserAccount getByAccountNumber(long recipientAccountNumber) {
        return userAccountDAO.getByAccountNumber(recipientAccountNumber);
    }

    /**
     * Method to pay by id.
     *
     * @param userId The user id.
     * @param amount The amount.
     * @return The UserAccount object.
     */
    public UserAccount payById(int userId, double amount) {
        UserAccount userAccount = userAccountDAO.getById(userId);
        if (userAccount.getBalance() >= amount) {
            userAccount.setBalance(userAccount.getBalance() - amount);
            return userAccount;
        }
        return null;
    }

    /**
     * Method to update balance by account number.
     *
     * @param amount The amount.
     * @param recipientAccountNumber The account number of recipient.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateByAccount(double amount, long recipientAccountNumber) {
        return userAccountDAO.updateByAccount(amount, recipientAccountNumber);
    }

    /**
     * Method to update deposit status by id.
     *
     * @param userId The user id.
     * @param decision The decision.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateDepositStatusById(int userId, boolean decision) {
        return userAccountDAO.updateDepositStatusById(userId, decision);
    }
}
