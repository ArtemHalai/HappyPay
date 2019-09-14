package model;

import java.util.Objects;

import static enums.OperationType.CREDIT_APPROVEMENT;

public class CreditApprovementOperation extends Operation {

    private boolean decision;

    public CreditApprovementOperation() {
        this.operationType=CREDIT_APPROVEMENT.getName();
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
        CreditApprovementOperation that = (CreditApprovementOperation) o;
        return decision == that.decision;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), decision);
    }
}
