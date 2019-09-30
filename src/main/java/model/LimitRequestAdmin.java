package model;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents a LimitRequestAdmin object.
 */
public class LimitRequestAdmin {

    private int userId;
    private Date term;
    private double balance;
    private double amount;
    private boolean decision;
    private double arrears;

    /**
     * Gets the value of {@link #userId}.
     *
     * @return the value of {@link #userId}.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This is a setter which sets the user id.
     *
     * @param userId - the user id to be set
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
     * Gets the value of {@link #amount}.
     *
     * @return the value of {@link #amount}.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * This is a setter which sets the amount.
     *
     * @param amount - the amount to be set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the value of {@link #decision}.
     *
     * @return the value of {@link #decision}.
     */
    public boolean isDecision() {
        return decision;
    }

    /**
     * This is a setter which sets the decision.
     *
     * @param decision - the decision to be set
     */
    public void setDecision(boolean decision) {
        this.decision = decision;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LimitRequestAdmin that = (LimitRequestAdmin) o;
        return userId == that.userId &&
                Double.compare(that.balance, balance) == 0 &&
                Double.compare(that.amount, amount) == 0 &&
                decision == that.decision &&
                Double.compare(that.arrears, arrears) == 0 &&
                Objects.equals(term, that.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, term, balance, amount, decision, arrears);
    }
}
