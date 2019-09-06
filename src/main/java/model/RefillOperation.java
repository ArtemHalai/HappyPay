package model;

import java.util.Objects;

public class RefillOperation extends Operation {

    private double amount;
    private long accountNumber;
    private long senderIBAN;

    public long getSenderIBAN() {
        return senderIBAN;
    }

    public void setSenderIBAN(long senderIBAN) {
        this.senderIBAN = senderIBAN;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RefillOperation that = (RefillOperation) o;
        return Double.compare(that.amount, amount) == 0 &&
                accountNumber == that.accountNumber &&
                senderIBAN == that.senderIBAN;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount, accountNumber, senderIBAN);
    }
}
