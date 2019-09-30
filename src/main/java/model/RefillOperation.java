package model;

import java.util.Objects;

import static enums.OperationType.REFILL_OPERATION;

/**
 * Represents a RefillOperation object.
 * Extends Operation class.
 *
 * @see Operation
 */
public class RefillOperation extends Operation {

    private String senderAccountType;

    /**
     * Creates a RefillOperation object without params.
     */
    public RefillOperation() {
        this.operationType = REFILL_OPERATION.getName();
    }

    /**
     * Gets the value of {@link #senderAccountType}.
     *
     * @return the value of {@link #senderAccountType}.
     */
    public String getSenderAccountType() {
        return senderAccountType;
    }

    /**
     * This is a setter which sets the sender account type.
     *
     * @param senderAccountType - the sender account type to be set
     */
    public void setSenderAccountType(String senderAccountType) {
        this.senderAccountType = senderAccountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RefillOperation that = (RefillOperation) o;
        return Objects.equals(senderAccountType, that.senderAccountType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), senderAccountType);
    }
}
