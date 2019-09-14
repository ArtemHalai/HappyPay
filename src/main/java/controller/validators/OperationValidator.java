package controller.validators;

import java.util.HashMap;
import java.util.Map;
import static enums.Errors.AMOUNT_ERROR;
import static enums.Fields.AMOUNT;

public class OperationValidator implements Validator {

    private double amount;
    private Map<String, String> errors = new HashMap<>();

    public OperationValidator(double amount) {
        this.amount = amount;
    }

    @Override
    public Map<String, String> validate() {
        validateAmount();
        return errors;
    }

    private void validateAmount() {
        if (amount <= 0) {
            errors.put(AMOUNT.getName(), AMOUNT_ERROR.getName());
        }
    }
}
