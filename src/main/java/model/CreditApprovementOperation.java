package model;

import java.util.Objects;

public class CreditApprovementOperation extends Operation {

    private int managerId;
    private double amount;
    private boolean decision;

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CreditApprovementOperation creditApprovement = (CreditApprovementOperation) o;
        return managerId == creditApprovement.managerId &&
                Double.compare(creditApprovement.amount, amount) == 0 &&
                decision == creditApprovement.decision;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), managerId, amount, decision);
    }
}
