package model;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents a CreditRequest object.
 */
public class CreditRequest {
    private int userId;
    private double amount;
    private boolean decision;
    private Date operationDate;

    /**
     * Gets the value of {@link #operationDate}.
     *
     * @return the value of {@link #operationDate}.
     */
    public Date getOperationDate() {
        return operationDate;
    }

    /**
     * This is a setter which sets the operation date.
     *
     * @param operationDate - the operation date to be set
     */
    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

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
    public boolean getDecision() {
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
        CreditRequest that = (CreditRequest) o;
        return userId == that.userId &&
                Double.compare(that.amount, amount) == 0 &&
                decision == that.decision &&
                Objects.equals(operationDate, that.operationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, amount, decision, operationDate);
    }
}
