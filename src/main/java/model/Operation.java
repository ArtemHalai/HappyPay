package model;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents an abstract Operation class.
 */
public abstract class Operation {

    protected int userId;
    protected Date date;
    protected double amount;
    protected String operationType;

    /**
     * Gets the value of {@link #operationType}.
     *
     * @return the value of {@link #operationType}.
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * This is a setter which sets the operation type.
     *
     * @param operationType - the operation type to be set
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
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
     * Gets the value of {@link #date}.
     *
     * @return the value of {@link #date}.
     */
    public Date getDate() {
        return date;
    }

    /**
     * This is a setter which sets the date.
     *
     * @param date - the date to be set
     */
    public void setDate(Date date) {
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return userId == operation.userId &&
                Double.compare(operation.amount, amount) == 0 &&
                Objects.equals(date, operation.date) &&
                Objects.equals(operationType, operation.operationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, date, amount, operationType);
    }
}
