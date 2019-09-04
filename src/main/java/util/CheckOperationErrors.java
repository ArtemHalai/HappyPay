package util;

import controller.validators.OperationValidator;
import controller.validators.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static enums.Attributes.ERRORS;

public class CheckOperationErrors {

    public static boolean errorsEmpty(HttpServletRequest request, double amount, long recipientAccountNumber){
        Validator operationValidator = new OperationValidator(amount, recipientAccountNumber);
        Map<String, String> errors = operationValidator.validate();
        if (!errors.isEmpty()) {
            request.setAttribute(ERRORS.getName(), errors);
            return false;
        }
        return true;
    }
}
