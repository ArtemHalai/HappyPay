package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static enums.OperationType.TRANSFER_OPERATION;

@Data
@EqualsAndHashCode(callSuper=true)
public class TransferOperation extends Operation {

    private long recipientAccountNumber;

    public TransferOperation() {
        this.operationType = TRANSFER_OPERATION.getName();
    }
}
