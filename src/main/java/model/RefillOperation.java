package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static enums.OperationType.REFILL_OPERATION;

@Data
@EqualsAndHashCode(callSuper = true)
public class RefillOperation extends Operation {
    private String senderAccountType;

    public RefillOperation() {
        this.operationType = REFILL_OPERATION.getName();
    }
}
