package controller.validators;

import java.util.HashMap;
import java.util.Map;

import static enums.Errors.ACCOUNT_NUMBER_ERROR;
import static enums.Errors.AMOUNT_ERROR;
import static enums.Fields.ACCOUNT_NUMBER;
import static enums.Fields.AMOUNT;
import static enums.Regex.ACCOUNT_NUMBER_REGEX;

public class OperationValidator implements Validator {

    private long number;
    private double amount;
    private Map<String, String> errors = new HashMap<>();

    public OperationValidator(double amount, long number) {
        this.amount = amount;
        this.number = number;
    }

    @Override
    public Map<String, String> validate() {
        validateAmount();
        validateAccountNumber();
        return errors;
    }

    private void validateAccountNumber() {
        if (number <= 0) {
            errors.put(ACCOUNT_NUMBER.getName(), ACCOUNT_NUMBER_ERROR.getName());
        }
    }

    private void validateAmount() {
        if (amount <= 0) {
            errors.put(AMOUNT.getName(), AMOUNT_ERROR.getName());
        }
    }
}
