package model.calculation;

import model.DepositAccount;
import java.time.temporal.ChronoUnit;

public class DepositCalculator implements Calculator {

    private DepositAccount depositAccount;

    public DepositCalculator(DepositAccount depositAccount) {
        this.depositAccount = depositAccount;
    }

    @Override
    public double calculate() {
        switch (depositAccount.getDepositEnum()) {
            case YEAR:
                return depositYear();
            default:
                return depositYear();
        }
    }

    private double depositYear() {
        long days = ChronoUnit.DAYS.between(depositAccount.getStartDate().toLocalDate(), depositAccount.getTerm().toLocalDate());
        return depositAccount.getBalance() * depositAccount.getRate() * days / (days * 100) + depositAccount.getBalance();
    }
}
