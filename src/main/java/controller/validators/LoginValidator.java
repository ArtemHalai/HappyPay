package controller.validators;

import java.util.HashMap;
import java.util.Map;

import static enums.Attributes.PASSWORD;
import static enums.Attributes.USERNAME;
import static enums.Errors.PASSWORD_DOES_NOT_MATCH;
import static enums.Errors.WRONG_USERNAME;
import static enums.Regex.PASSWORD_REGEX;
import static enums.Regex.USERNAME_REGEX;

public class LoginValidator implements Validator {

    private String username;
    private String password;
    protected Map<String, String> errors = new HashMap<>();

    public LoginValidator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Map<String, String> validate() {
        validateUsername();
        validatePassword();
        return errors;
    }

    protected void validateUsername() {
        if (!username.matches(USERNAME_REGEX.getName())) {
            errors.put(USERNAME.getName(), WRONG_USERNAME.getName());
        }
    }

    protected void validatePassword() {
        if (!password.matches(PASSWORD_REGEX.getName())) {
            errors.put(PASSWORD.getName(), PASSWORD_DOES_NOT_MATCH.getName());
        }
    }
}
