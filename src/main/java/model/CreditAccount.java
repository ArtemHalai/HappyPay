package model;

import java.util.Objects;

import static enums.Currency.USD;

public class CreditAccount extends Account {

    private double limit;
    private double rate;
    private double arrears;
    private double interestCharges;

    public CreditAccount() {
        this.currency = USD.getName();
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getRate() {
        return rate;
    }

    public double getArrears() {
        return arrears;
    }

    public void setArrears(double arrears) {
        this.arrears = arrears;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getInterestCharges() {
        return interestCharges;
    }

    public void setInterestCharges(double interestCharges) {
        this.interestCharges = interestCharges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditAccount)) return false;
        if (!super.equals(o)) return false;
        CreditAccount creditAccount = (CreditAccount) o;
        return Double.compare(creditAccount.limit, limit) == 0 &&
                Double.compare(creditAccount.rate, rate) == 0 &&
                Double.compare(creditAccount.interestCharges, interestCharges) == 0 &&
                Double.compare(creditAccount.arrears, arrears) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), limit, rate, interestCharges, arrears);
    }
}
