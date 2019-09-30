package model.calculation;

import model.DepositAccount;
import java.time.temporal.ChronoUnit;

/**
 * The object used for calculating deposit interest charges on deposit account.
 * Implements Calculator.
 *
 * @see Calculator
 */
public class DepositCalculator implements Calculator {

    private DepositAccount depositAccount;

    /**
     * Sole constructor to initialize {@link #depositAccount}.
     *
     * @param depositAccount The DepositAccount object.
     */
    public DepositCalculator(DepositAccount depositAccount) {
        this.depositAccount = depositAccount;
    }

    /**
     * Method to calculate interest charges on deposit account.
     *
     * @return The double value result calculated by this method.
     * {@link #depositYear()}
     */
    @Override
    public double calculate() {
        switch (depositAccount.getDepositEnum()) {
            case YEAR:
                return depositYear();
            default:
                return depositYear();
        }
    }

    /**
     * Method to calculate interest charges on deposit account.
     *
     * @return The double value result calculated by this method.
     */
    private double depositYear() {
        long days = ChronoUnit.DAYS.between(depositAccount.getStartDate().toLocalDate(), depositAccount.getTerm().toLocalDate());
        return depositAccount.getBalance() * depositAccount.getRate() * days / (days * 100) + depositAccount.getBalance();
    }
}
