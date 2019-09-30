package controller.validators;

import java.util.HashMap;
import java.util.Map;
import static enums.Errors.AMOUNT_ERROR;
import static enums.Fields.AMOUNT;

/**
 * The object used for validating operation data.
 */
public class OperationValidator implements Validator {

    private double amount;
    private Map<String, String> errors = new HashMap<>();

    /**
     * Creates a OperationValidator object with the specified amount.
     *
     * @param amount The amount to validate.
     */
    public OperationValidator(double amount) {
        this.amount = amount;
    }

    /**
     * Method to validate operation data.
     *
     * @return The empty map if validation was successful, and map containing errors if something was invalid
     * during validation.
     */
    @Override
    public Map<String, String> validate() {
        validateAmount();
        return errors;
    }

    /**
     * Method to validate amount.
     * If amount is less or equal zero then put the message in map {@link #errors}.
     *
     * @see enums.Errors
     * @see enums.Fields
     */
    private void validateAmount() {
        if (amount <= 0) {
            errors.put(AMOUNT.getName(), AMOUNT_ERROR.getName());
        }
    }
}
