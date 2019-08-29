package model;

import java.util.Objects;

public class TransferOperation extends Operation {

    private String recipientAccountNumber;
    private double amount;

    public String getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(String recipientAccountNumber) {
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
                recipientAccountNumber.equals(transfer.recipientAccountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), recipientAccountNumber, amount);
    }
}
