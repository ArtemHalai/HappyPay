package controller.validators;

import java.util.Map;

/**
 * The object used for validating data.
 */
public interface Validator {

    /**
     * Method to validate different types of data.
     *
     * @return The empty map if validation was successful, and map containing errors if something was invalid
     * during validation.
     */
    Map<String, String> validate();
}
