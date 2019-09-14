package model;

import java.util.Objects;

import static enums.OperationType.TRANSFER_OPERATION;

public class TransferOperation extends Operation {

    private long recipientAccountNumber;

    public TransferOperation() {
        this.operationType = TRANSFER_OPERATION.getName();
    }

    public long getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

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
