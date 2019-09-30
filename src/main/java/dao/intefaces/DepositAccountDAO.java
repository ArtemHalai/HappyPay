package dao.intefaces;

import model.DepositAccount;

import java.util.List;

/**
 * Define a data access object used for executing deposit account's requests to database.
 */
public interface DepositAccountDAO extends DAO<DepositAccount> {

    /**
     * Method to add deposit account.
     *
     * @param depositAccount The DepositAccount object.
     * @return <code>true</code> if deposit account was added; <code>false</code> otherwise.
     * @see DepositAccount
     */
    boolean add(DepositAccount depositAccount);

    /**
     * Method to update balance of deposit account by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if balance was updated; <code>false</code> otherwise.
     */
    boolean updateBalanceById(double amount, int userId);

    /**
     * Method to delete deposit account by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if deposit account was deleted; <code>false</code> otherwise.
     */
    boolean deleteDepositAccount(int userId);

    /**
     * Method to get count of all deposit accounts in database by user id.
     *
     * @param userId The user id.
     * @return The int value representing amount of all deposit accounts in database by given user id.
     */
    int count(int userId);

    /**
     * Method to get all deposit accounts.
     *
     * @return The list containing all deposit accounts.
     * @see DepositAccount
     */
    List<DepositAccount> getAll();
}
