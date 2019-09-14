package model.calculation;

import model.CreditAccount;

import java.util.Calendar;

public class CreditCalculator implements Calculator {

    private CreditAccount creditAccount;

    public CreditCalculator(CreditAccount creditAccount) {
        this.creditAccount = creditAccount;
    }

    @Override
    public double calculate() {
        int accruedInterestPeriod = 1;
        int days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);
        return creditAccount.getRate() / 100 / days * accruedInterestPeriod * creditAccount.getArrears();
    }
}
