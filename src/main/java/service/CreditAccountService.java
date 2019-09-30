package service;

import dao.intefaces.CreditAccountDAO;
import model.CreditAccount;

import java.util.List;

/**
 * A class that works with CreditAccountDAO.
 *
 * @see CreditAccountDAO
 */
public class CreditAccountService {

    private CreditAccountDAO creditAccountDAO;

    /**
     * Method to set CreditAccountDAO object {@link #creditAccountDAO}.
     *
     * @param creditAccountDAO The CreditAccountDAO object.
     * @see CreditAccountDAO
     */
    public void setCreditAccountDAO(CreditAccountDAO creditAccountDAO) {
        this.creditAccountDAO = creditAccountDAO;
    }

    /**
     * Method to add CreditAccount object.
     *
     * @param creditAccount The CreditAccount object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see CreditAccount
     */
    public boolean add(CreditAccount creditAccount) {
        return creditAccountDAO.add(creditAccount);
    }

    /**
     * Method to update arrears by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateArrears(double amount, int userId) {
        return creditAccountDAO.payArrears(amount, userId);
    }

    /**
     * Method to update balance by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateBalanceById(double amount, int userId){
        return creditAccountDAO.updateBalanceById(amount, userId);
    }

    /**
     * Method to get CreditAccount object by user id.
     *
     * @param id The user id.
     * @return The CreditAccount object.
     * @see CreditAccount
     */
    public CreditAccount getById(int id) {
        return creditAccountDAO.getById(id);
    }

    /**
     * Method to get all credit accounts.
     *
     * @return The list of CreditAccount objects.
     * @see CreditAccount
     */
    public List<CreditAccount> getAll() {
        return creditAccountDAO.getAll();
    }

    /**
     * Method to update interest charges by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean updateInterestCharges(double amount, int userId) {
        return creditAccountDAO.updateInterestCharges(amount, userId);
    }
}
