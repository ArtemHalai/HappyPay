package controller.validators;

import model.ClientDetails;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

import static enums.Attributes.*;
import static enums.Errors.*;
import static enums.Regex.NAME_REGEX;
import static enums.Regex.PHONE_NUMBER_REGEX;

/**
 * The object used for validating registration data.
 */
public class RegistrationValidator extends LoginValidator {

    private ClientDetails clientDetails;

    /**
     * Creates a RegistrationValidator object with the specified client details object {@link #clientDetails}.
     *
     * @param clientDetails The client details object containing registration info.
     * @see ClientDetails
     */
    public RegistrationValidator(ClientDetails clientDetails) {
        super(clientDetails.getUsername(), clientDetails.getPassword());
        this.clientDetails = clientDetails;
    }

    /**
     * Method to validate registration info data.
     *
     * @return The empty map if validation was successful, and map containing errors if something was invalid
     * during validation.
     * @see LoginValidator
     */
    @Override
    public Map<String, String> validate() {
        validateName();
        validateSurname();
        validateUsername();
        validatePhoneNumber();
        validatePassword();
        validateBirthday();
        return errors;
    }

    /**
     * Method to validate name.
     * If name doesn't match regular expression then put the message in map {@link #errors}.
     *
     * @see enums.Errors
     * @see enums.Attributes
     * @see enums.Regex
     */
    private void validateName() {
        String name = clientDetails.getName();

        if (!name.matches(NAME_REGEX.getName())) {
            errors.put(NAME.getName(), NAME_DOES_NOT_MATCH.getName());
        }
    }

    /**
     * Method to validate surname.
     * If surname doesn't match regular expression then put the message in map {@link #errors}.
     *
     * @see enums.Errors
     * @see enums.Attributes
     * @see enums.Regex
     */
    private void validateSurname() {
        String surname = clientDetails.getSurname();

        if (!surname.matches(NAME_REGEX.getName())) {
            errors.put(SURNAME.getName(), SURNAME_DOES_NOT_MATCH.getName());
        }
    }

    /**
     * Method to validate phone number.
     * If phone number doesn't match regular expression then put the message in map {@link #errors}.
     *
     * @see enums.Errors
     * @see enums.Attributes
     * @see enums.Regex
     */
    private void validatePhoneNumber() {
        String phoneNumber = clientDetails.getPhoneNumber();

        if (!phoneNumber.matches(PHONE_NUMBER_REGEX.getName())) {
            errors.put(PHONE_NUMBER.getName(), PHONE_NUMBER_DOES_NOT_MATCH.getName());
        }
    }

    /**
     * Method to validate birthday.
     * If client's age is less than 18 then put the message in map {@link #errors}.
     *
     * @see enums.Errors
     * @see enums.Attributes
     */
    private void validateBirthday() {
        Date birthday = clientDetails.getBirthday();
        Calendar calendar = Calendar.getInstance();
        Calendar calendarBirthday = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendarBirthday.setTimeInMillis(birthday.getTime());
        int years = calendar.get(Calendar.YEAR) - calendarBirthday.get(Calendar.YEAR);
        if (years < 18) {
            errors.put(BIRTHDAY.getName(), BIRTHDAY_DOES_NOT_MATCH.getName());
        }
    }

}
