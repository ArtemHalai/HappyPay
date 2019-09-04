package model;

import java.util.Objects;

public class RefillOperation extends Operation {

    private double amount;
    private long accountNumber;

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
        if (!(o instanceof RefillOperation)) return false;
        if (!super.equals(o)) return false;
        RefillOperation refill = (RefillOperation) o;
        return Double.compare(refill.amount, amount) == 0 &&
                accountNumber == refill.accountNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount, accountNumber);
    }
}
