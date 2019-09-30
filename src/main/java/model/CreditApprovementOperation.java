package model;

import java.util.Objects;

import static enums.OperationType.CREDIT_APPROVEMENT;

/**
 * Represents a CreditApprovementOperation object.
 * Extends Operation class.
 *
 * @see Operation
 */
public class CreditApprovementOperation extends Operation {

    private boolean decision;

    /**
     * Creates a CreditApprovementOperation object without params.
     */
    public CreditApprovementOperation() {
        this.operationType=CREDIT_APPROVEMENT.getName();
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
        if (!super.equals(o)) return false;
        CreditApprovementOperation that = (CreditApprovementOperation) o;
        return decision == that.decision;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), decision);
    }
}
