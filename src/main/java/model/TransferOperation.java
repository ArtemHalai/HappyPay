package model;

import java.util.Objects;

public class TransferOperation extends Operation {

    private long recipientAccountNumber;
    private double amount;

    public long getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(long recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransferOperation)) return false;
        if (!super.equals(o)) return false;
        TransferOperation transfer = (TransferOperation) o;
        return Double.compare(transfer.amount, amount) == 0 &&
                recipientAccountNumber == transfer.recipientAccountNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), recipientAccountNumber, amount);
    }
}
