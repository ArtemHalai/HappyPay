package util;

import controller.validators.OperationValidator;
import controller.validators.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static enums.Attributes.ERRORS;

public class CheckOperationErrors {

    private CheckOperationErrors() {

    }

    public static Map<String, String> errorsEmpty(HttpServletRequest request, double amount) {
        Validator operationValidator = new OperationValidator(amount);
        Map<String, String> errors = operationValidator.validate();
        request.setAttribute(ERRORS.getName(), errors);
        return errors;
    }
}
