package model;

import java.util.Objects;

import static enums.Currency.USD;

/**
 * Represents a CreditAccount object.
 */
public class CreditAccount extends UserAccount {

    private double limit;
    private double rate;
    private double arrears;
    private double interestCharges;
    private String currency;

    /**
     * Creates a CreditAccount object without params.
     */
    public CreditAccount() {
        this.currency = USD.getName();
    }

    /**
     * Gets the value of {@link #limit}.
     *
     * @return the value of {@link #limit}.
     */
    public double getLimit() {
        return limit;
    }

    /**
     * This is a setter which sets the limit.
     *
     * @param limit - the limit to be set
     */
    public void setLimit(double limit) {
        this.limit = limit;
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
     * Gets the value of {@link #arrears}.
     *
     * @return the value of {@link #arrears}.
     */
    public double getArrears() {
        return arrears;
    }

    /**
     * This is a setter which sets the arrears.
     *
     * @param arrears - the arrears to be set
     */
    public void setArrears(double arrears) {
        this.arrears = arrears;
    }

    /**
     * Gets the value of {@link #interestCharges}.
     *
     * @return the value of {@link #interestCharges}.
     */
    public double getInterestCharges() {
        return interestCharges;
    }

    /**
     * This is a setter which sets the interest charges.
     *
     * @param interestCharges - the interest charges to be set
     */
    public void setInterestCharges(double interestCharges) {
        this.interestCharges = interestCharges;
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
