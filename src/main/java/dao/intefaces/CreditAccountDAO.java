package dao.intefaces;

import model.CreditAccount;

import java.util.List;

/**
 * Define a data access object used for executing credit account's requests to database.
 */
public interface CreditAccountDAO extends DAO<CreditAccount> {

    /**
     * Method to add credit account.
     *
     * @param creditAccount The CreditAccount object.
     * @return <code>true</code> if credit account was added; <code>false</code> otherwise.
     * @see CreditAccount
     */
    boolean add(CreditAccount creditAccount);

    /**
     * Method to update balance of credit account by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if balance was updated; <code>false</code> otherwise.
     */
    boolean updateBalanceById(double amount, int userId);

    /**
     * Method to pay arrears by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if arrears have been payed; <code>false</code> otherwise.
     */
    boolean payArrears(double amount, int userId);

    /**
     * Method to get all credit accounts.
     *
     * @return The list containing all credit accounts.
     * @see CreditAccount
     */
    List<CreditAccount> getAll();

    /**
     * Method to update interest charges of credit account by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if interest charges were updated; <code>false</code> otherwise.
     */
    boolean updateInterestCharges(double amount, int userId);
}
