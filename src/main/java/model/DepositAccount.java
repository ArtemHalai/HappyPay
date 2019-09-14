package model;

import enums.DepositEnum;

import java.sql.Date;
import java.util.Objects;

import static enums.Currency.USD;

public class DepositAccount extends UserAccount {

    private double rate;
    private Date startDate;
    private Date term;
    private String currency;
    private double balance;
    private DepositEnum depositEnum;

    public DepositAccount() {
        this.currency = USD.getName();
        this.startDate = new Date(System.currentTimeMillis());
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getTerm() {
        return term;
    }

    public void setTerm(Date term) {
        this.term = term;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public DepositEnum getDepositEnum() {
        return depositEnum;
    }

    public void setDepositEnum(DepositEnum depositEnum) {
        this.depositEnum = depositEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DepositAccount that = (DepositAccount) o;
        return Double.compare(that.rate, rate) == 0 &&
                Double.compare(that.balance, balance) == 0 &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(term, that.term) &&
                Objects.equals(currency, that.currency) &&
                depositEnum == that.depositEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rate, startDate, term, currency, balance, depositEnum);
    }
}
