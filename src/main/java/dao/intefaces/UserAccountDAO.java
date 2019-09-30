package dao.intefaces;

import model.UserAccount;

/**
 * Define a data access object used for executing user account's requests to database.
 */
public interface UserAccountDAO extends DAO<UserAccount> {

    /**
     * Method to add user account.
     *
     * @param userAccount The UserAccount object.
     * @return <code>true</code> if user account was added; <code>false</code> otherwise.
     * @see UserAccount
     */
    boolean add(UserAccount userAccount);

    /**
     * Method to update balance of account by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if balance was updated; <code>false</code> otherwise.
     */
    boolean updateBalanceById(double amount, int userId);

    /**
     * Method to update credit status by user id.
     *
     * @param decision The decision.
     * @param id The user id.
     * @return <code>true</code> if status was updated; <code>false</code> otherwise.
     */
    boolean updateCreditStatusById(int id, boolean decision);

    /**
     * Method to update term of account by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if term was updated; <code>false</code> otherwise.
     */
    boolean updateTerm(int userId);

    /**
     * Method to get user account by account number.
     *
     * @param recipientAccountNumber The recipient account number.
     * @return The user account object.
     * @see UserAccount
     */
    UserAccount getByAccountNumber(long recipientAccountNumber);

    /**
     * Method to update balance by account number.
     *
     * @param recipientAccountNumber The recipient account number to be updated.
     * @param amount The amount to set.
     * @return <code>true</code> if balance was updated; <code>false</code> otherwise.
     */
    boolean updateByAccount(double amount, long recipientAccountNumber);

    /**
     * Method to update deposit status by user id.
     *
     * @param decision The decision.
     * @param userId The user id.
     * @return <code>true</code> if status was updated; <code>false</code> otherwise.
     */
    boolean updateDepositStatusById(int userId, boolean decision);
}
