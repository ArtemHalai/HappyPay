package model;

import java.util.Objects;

public class RefillOperation extends Operation {

    private double amount;
    private String accountNumber;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefillOperation)) return false;
        if (!super.equals(o)) return false;
        RefillOperation refill = (RefillOperation) o;
        return Double.compare(refill.amount, amount) == 0 &&
                accountNumber.equals(refill.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount, accountNumber);
    }
}
