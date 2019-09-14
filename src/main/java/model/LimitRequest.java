package model;

import java.sql.Date;
import java.util.Objects;

public class LimitRequest {

    private int userId;
    private double amount;
    private Date operationDate;
    private boolean decision;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public boolean getDecision() {
        return decision;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LimitRequest that = (LimitRequest) o;
        return userId == that.userId &&
                Double.compare(that.amount, amount) == 0 &&
                decision == that.decision &&
                Objects.equals(operationDate, that.operationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, amount, operationDate, decision);
    }
}
