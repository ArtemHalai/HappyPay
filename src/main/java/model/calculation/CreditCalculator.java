package model.calculation;

import model.CreditAccount;

import java.util.Calendar;

/**
 * The object used for calculating interest charges on credit account.
 * Implements Calculator.
 *
 * @see Calculator
 */
public class CreditCalculator implements Calculator {

    private CreditAccount creditAccount;

    /**
     * Sole constructor to initialize {@link #creditAccount}.
     *
     * @param creditAccount The CreditAccount object.
     */
    public CreditCalculator(CreditAccount creditAccount) {
        this.creditAccount = creditAccount;
    }

    /**
     * Method to calculate interest charges on credit account.
     *
     * @return The double value result calculated by this method.
     */
    @Override
    public double calculate() {
        int accruedInterestPeriod = 1;
        int days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);
        return creditAccount.getRate() / 100 / days * accruedInterestPeriod * creditAccount.getArrears();
    }
}
