package model;

import java.sql.Date;
import java.util.Objects;

public class CreditRequestAdmin {
    private int userId;
    private double balance;
    private Date validity;
    private double amount;
    private boolean decision;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAmount() {
        return amount;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isDecision() {
        return decision;
    }

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
