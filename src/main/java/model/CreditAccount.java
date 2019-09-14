package model;

import java.util.Objects;

import static enums.Currency.USD;

public class CreditAccount extends UserAccount {

    private double limit;
    private double rate;
    private double arrears;
    private double interestCharges;
    private String currency;

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

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getArrears() {
        return arrears;
    }

    public void setArrears(double arrears) {
        this.arrears = arrears;
    }

    public double getInterestCharges() {
        return interestCharges;
    }

    public void setInterestCharges(double interestCharges) {
        this.interestCharges = interestCharges;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CreditAccount that = (CreditAccount) o;
        return Double.compare(that.limit, limit) == 0 &&
                Double.compare(that.rate, rate) == 0 &&
                Double.compare(that.arrears, arrears) == 0 &&
                Double.compare(that.interestCharges, interestCharges) == 0 &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), limit, rate, arrears, interestCharges, currency);
    }
}
