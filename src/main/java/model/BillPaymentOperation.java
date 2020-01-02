package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static enums.OperationType.BILL_PAYMENT;

@Data
@EqualsAndHashCode(callSuper = true)
public class BillPaymentOperation extends Operation {
    private long billNumber;
    private String purpose;

    public BillPaymentOperation() {
        this.operationType = BILL_PAYMENT.getName();
    }
}
