package util;

import controller.validators.OperationValidator;
import controller.validators.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static enums.Attributes.ERRORS;

/**
 * Define a class which checks operation with errors.
 */
public class CheckOperationErrors {

    /**
     * Method to check operation with errors.
     *
     * @param request The HttpServletRequest object.
     * @param amount  The amount to check.
     * @return The empty map if validation was successful, and map containing errors if something was invalid
     * during validation.
     */
    public static Map<String, String> errorsEmpty(HttpServletRequest request, double amount) {
        Validator operationValidator = new OperationValidator(amount);
        Map<String, String> errors = operationValidator.validate();
        request.setAttribute(ERRORS.getName(), errors);
        return errors;
    }
}
