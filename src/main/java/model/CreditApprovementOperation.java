package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static enums.OperationType.CREDIT_APPROVEMENT;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreditApprovementOperation extends Operation {
    private boolean decision;

    public CreditApprovementOperation() {
        this.operationType = CREDIT_APPROVEMENT.getName();
    }
}
