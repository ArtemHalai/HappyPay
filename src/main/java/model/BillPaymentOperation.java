package model;

import java.util.Objects;

import static enums.OperationType.BILL_PAYMENT;

public class BillPaymentOperation extends Operation {

    private long billNumber;
    private String purpose;

    public BillPaymentOperation() {
        this.operationType = BILL_PAYMENT.getName();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BillPaymentOperation that = (BillPaymentOperation) o;
        return billNumber == that.billNumber &&
                Objects.equals(purpose, that.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), billNumber, purpose);
    }
}
