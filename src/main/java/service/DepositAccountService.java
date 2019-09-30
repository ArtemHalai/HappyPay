package service;

import dao.intefaces.DepositAccountDAO;
import model.DepositAccount;

import java.util.List;

/**
 * A class that works with DepositAccountDAO.
 *
 * @see DepositAccountDAO
 */
public class DepositAccountService {

    private DepositAccountDAO depositAccountDAO;

    /**
     * Method to set DepositAccountDAO object {@link #depositAccountDAO}.
     *
     * @param depositAccountDAO The DepositAccountDAO object.
     * @see DepositAccountDAO
     */
    public void setDepositAccountDAO(DepositAccountDAO depositAccountDAO) {
        this.depositAccountDAO = depositAccountDAO;
    }

    /**
     * Method to add DepositAccount object.
     *
     * @param depositAccount The DepositAccount object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see DepositAccount
     */
    public boolean add(DepositAccount depositAccount) {
        return depositAccountDAO.add(depositAccount);
    }

    /**
     * Method to update balance by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateBalanceById(double amount, int userId) {
        return depositAccountDAO.updateBalanceById(amount, userId);
    }

    /**
     * Method to get DepositAccount object by user id.
     *
     * @param id The user id.
     * @return The DepositAccount object.
     * @see DepositAccount
     */
    public DepositAccount getById(int id) {
        return depositAccountDAO.getById(id);
    }

    /**
     * Method to delete DepositAccount by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean deleteDepositAccount(int userId) {
        return depositAccountDAO.deleteDepositAccount(userId);
    }

    /**
     * Method to get all deposit accounts.
     *
     * @return The list of DepositAccount objects.
     * @see DepositAccount
     */
    public List<DepositAccount> getAll() {
        return depositAccountDAO.getAll();
    }
}
