package model;

import java.sql.Date;
import java.util.Objects;

public class LimitRequestAdmin {
    private int userId;
    private Date term;
    private double balance;
    private double amount;
    private boolean decision;
    private double arrears;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTerm() {
        return term;
    }

    public void setTerm(Date term) {
        this.term = term;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isDecision() {
        return decision;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }

    public double getArrears() {
        return arrears;
    }

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
