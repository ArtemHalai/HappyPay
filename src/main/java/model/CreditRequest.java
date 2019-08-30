package model;

import java.util.Objects;

public class CreditRequest {
    private int userId;
    private double amount;
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
        CreditRequest that = (CreditRequest) o;
        return userId == that.userId &&
                Double.compare(that.amount, amount) == 0 &&
                decision == that.decision;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, amount, decision);
    }
}
