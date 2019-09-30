package model;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents a CreditRequestAdmin object.
 */
public class CreditRequestAdmin {

    private int userId;
    private double balance;
    private Date validity;
    private double amount;
    private boolean decision;

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
     * Gets the value of {@link #validity}.
     *
     * @return the value of {@link #validity}.
     */
    public Date getValidity() {
        return validity;
    }

    /**
     * This is a setter which sets the validity.
     *
     * @param validity - the validity to be set
     */
    public void setValidity(Date validity) {
        this.validity = validity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditRequestAdmin that = (CreditRequestAdmin) o;
        return userId == that.userId &&
                Double.compare(that.balance, balance) == 0 &&
                Double.compare(that.amount, amount) == 0 &&
                decision == that.decision &&
                Objects.equals(validity, that.validity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, balance, validity, amount, decision);
    }
}
