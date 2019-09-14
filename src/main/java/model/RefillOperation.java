package model;

import java.util.Objects;

import static enums.OperationType.REFILL_OPERATION;

public class RefillOperation extends Operation {

    private String senderAccountType;

    public RefillOperation() {
        this.operationType = REFILL_OPERATION.getName();
    }

    public String getSenderAccountType() {
        return senderAccountType;
    }

    public void setSenderAccountType(String senderAccountType) {
        this.senderAccountType = senderAccountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RefillOperation that = (RefillOperation) o;
        return Objects.equals(senderAccountType, that.senderAccountType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), senderAccountType);
    }
}
