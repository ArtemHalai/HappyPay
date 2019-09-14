package model;

import java.sql.Date;
import java.util.Objects;

public abstract class Operation {

    protected int userId;
    protected Date date;
    protected double amount;
    protected String operationType;

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

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
