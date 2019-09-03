package controller.validators;

import model.ClientDetails;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

import static enums.Attributes.*;
import static enums.Errors.*;
import static enums.Regex.NAME_REGEX;
import static enums.Regex.PHONE_NUMBER_REGEX;

public class RegistrationValidator extends LoginValidator {

    private ClientDetails clientDetails;

    public RegistrationValidator(ClientDetails clientDetails) {
        super(clientDetails.getUsername(), clientDetails.getPassword());
        this.clientDetails = clientDetails;
    }

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

    private void validateName() {
        String name = clientDetails.getName();

        if (!name.matches(NAME_REGEX.getName())) {
            errors.put(NAME.getName(), NAME_DOES_NOT_MATCH.getName());
        }
    }

    private void validateSurname() {
        String surname = clientDetails.getSurname();

        if (!surname.matches(NAME_REGEX.getName())) {
            errors.put(SURNAME.getName(), SURNAME_DOES_NOT_MATCH.getName());
        }
    }

    private void validatePhoneNumber() {
        String phoneNumber = clientDetails.getMobilePhone();

        if (!phoneNumber.matches(PHONE_NUMBER_REGEX.getName())) {
            errors.put(PHONE_NUMBER.getName(), PHONE_NUMBER_DOES_NOT_MATCH.getName());
        }
    }

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
