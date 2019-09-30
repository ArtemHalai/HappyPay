package controller.validators;

import java.util.HashMap;
import java.util.Map;

import static enums.Attributes.PASSWORD;
import static enums.Attributes.USERNAME;
import static enums.Errors.PASSWORD_DOES_NOT_MATCH;
import static enums.Errors.WRONG_USERNAME;
import static enums.Regex.PASSWORD_REGEX;
import static enums.Regex.USERNAME_REGEX;

/**
 * The object used for validating login data.
 */
public class LoginValidator implements Validator {

    private String username;
    private String password;
    protected Map<String, String> errors = new HashMap<>();

    /**
     * Creates a LoginValidator object with the specified username, password.
     *
     * @param username The username of login info.
     * @param password The password of login info.
     */
    public LoginValidator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Method to validate login info data.
     *
     * @return The empty map if validation was successful, and map containing errors if something was invalid
     * during validation.
     */
    @Override
    public Map<String, String> validate() {
        validateUsername();
        validatePassword();
        return errors;
    }

    /**
     * Method to validate username.
     * If username doesn't match regular expression then put the message in map {@link #errors}.
     *
     * @see enums.Errors
     * @see enums.Attributes
     * @see enums.Regex
     */
    protected void validateUsername() {
        if (!username.matches(USERNAME_REGEX.getName())) {
            errors.put(USERNAME.getName(), WRONG_USERNAME.getName());
        }
    }

    /**
     * Method to validate password.
     * If password doesn't match regular expression then put the message in map {@link #errors}.
     *
     * @see enums.Errors
     * @see enums.Attributes
     * @see enums.Regex
     */
    protected void validatePassword() {
        if (!password.matches(PASSWORD_REGEX.getName())) {
            errors.put(PASSWORD.getName(), PASSWORD_DOES_NOT_MATCH.getName());
        }
    }
}
