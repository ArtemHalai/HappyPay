package model;

import enums.DepositEnum;

import java.sql.Date;
import java.util.Objects;

import static enums.Currency.USD;

/**
 * Represents a DepositAccount object.
 */
public class DepositAccount extends UserAccount {

    private double rate;
    private Date startDate;
    private Date term;
    private String currency;
    private double balance;
    private DepositEnum depositEnum;

    /**
     * Creates a DepositAccount object without params.
     */
    public DepositAccount() {
        this.currency = USD.getName();
        this.startDate = new Date(System.currentTimeMillis());
    }

    /**
     * Gets the value of {@link #startDate}.
     *
     * @return the value of {@link #startDate}.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This is a setter which sets the start date.
     *
     * @param startDate - the start date to be set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the value of {@link #rate}.
     *
     * @return the value of {@link #rate}.
     */
    public double getRate() {
        return rate;
    }

    /**
     * This is a setter which sets the rate.
     *
     * @param rate - the rate to be set
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Gets the value of {@link #term}.
     *
     * @return the value of {@link #term}.
     */
    public Date getTerm() {
        return term;
    }

    /**
     * This is a setter which sets the term.
     *
     * @param term - the term to be set
     */
    public void setTerm(Date term) {
        this.term = term;
    }

    /**
     * Gets the value of {@link #currency}.
     *
     * @return the value of {@link #currency}.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * This is a setter which sets the currency.
     *
     * @param currency - the currency to be set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets the value of {@link #balance}.
     *
     * @return the value of {@link #balance}.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * This is a setter which sets the balance.
     *
     * @param balance - the balance to be set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Gets the value of {@link #depositEnum}.
     *
     * @return the value of {@link #depositEnum}.
     */
    public DepositEnum getDepositEnum() {
        return depositEnum;
    }

    /**
     * This is a setter which sets the deposit enum.
     *
     * @param depositEnum - the deposit enum to be set
     */
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
