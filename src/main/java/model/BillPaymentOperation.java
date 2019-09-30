package model;

import java.util.Objects;

import static enums.OperationType.BILL_PAYMENT;

/**
 * Represents a BillPaymentOperation object.
 */
public class BillPaymentOperation extends Operation {

    private long billNumber;
    private String purpose;

    /**
     * Creates a BillPaymentOperation object without params.
     */
    public BillPaymentOperation() {
        this.operationType = BILL_PAYMENT.getName();
    }

    /**
     * Gets the value of {@link #billNumber}.
     *
     * @return the value of {@link #billNumber}.
     */
    public long getBillNumber() {
        return billNumber;
    }

    /**
     * This is a setter which sets the bill number.
     *
     * @param billNumber - the bill number to be set
     */
    public void setBillNumber(long billNumber) {
        this.billNumber = billNumber;
    }

    /**
     * Gets the value of {@link #purpose}.
     *
     * @return the value of {@link #purpose}.
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * This is a setter which sets the purpose.
     *
     * @param purpose - the purpose to be set
     */
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
