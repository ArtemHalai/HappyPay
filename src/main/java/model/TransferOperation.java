package model;

import java.util.Objects;

import static enums.OperationType.TRANSFER_OPERATION;

/**
 * Represents a TransferOperation object.
 * Extends Operation class.
 *
 * @see Operation
 */
public class TransferOperation extends Operation {

    private long recipientAccountNumber;

    /**
     * Creates a TransferOperation object without params.
     */
    public TransferOperation() {
        this.operationType = TRANSFER_OPERATION.getName();
    }

    /**
     * Gets the value of {@link #recipientAccountNumber}.
     *
     * @return the value of {@link #recipientAccountNumber}.
     */
    public long getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    /**
     * This is a setter which sets the recipient account number.
     *
     * @param recipientAccountNumber - the recipient account number to be set
     */
    public void setRecipientAccountNumber(long recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransferOperation that = (TransferOperation) o;
        return recipientAccountNumber == that.recipientAccountNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), recipientAccountNumber);
    }
}
