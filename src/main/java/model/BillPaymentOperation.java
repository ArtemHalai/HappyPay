package model;

import java.util.Objects;

public class BillPaymentOperation extends Operation {

    private long billNumber;
    private String purpose;
    private double amount;

    public long getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(long billNumber) {
        this.billNumber = billNumber;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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
        if (!(o instanceof BillPaymentOperation)) return false;
        if (!super.equals(o)) return false;
        BillPaymentOperation billPayment = (BillPaymentOperation) o;
        return Double.compare(billPayment.amount, amount) == 0 &&
                billNumber == billPayment.billNumber &&
                purpose.equals(billPayment.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), billNumber, purpose, amount);
    }
}
