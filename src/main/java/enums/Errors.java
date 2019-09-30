package enums;

/**
 *  Errors that can be used
 *  <li>{@link #WRONG_USERNAME}</li>
 *  <li>{@link #NAME_DOES_NOT_MATCH}</li>
 *  <li>{@link #SURNAME_DOES_NOT_MATCH}</li>
 *  <li>{@link #PHONE_NUMBER_DOES_NOT_MATCH}</li>
 *  <li>{@link #PASSWORD_DOES_NOT_MATCH}</li>
 *  <li>{@link #USER_ALREADY_EXISTS}</li>
 *  <li>{@link #USER_DOES_NOT_EXIST}</li>
 *  <li>{@link #ACCOUNT_NUMBER_ERROR}</li>
 *  <li>{@link #CREDIT_ERROR}</li>
 *  <li>{@link #AMOUNT_ERROR}</li>
 *  <li>{@link #NOT_ENOUGH_ERROR}</li>
 *  <li>{@link #ACCOUNT_ERROR}</li>
 *  <li>{@link #VALIDITY_ERROR}</li>
 *  <li>{@link #NO_REQUESTS_ERROR}</li>
 *  <li>{@link #NO_REFILL_OPERATION_ERROR}</li>
 *  <li>{@link #NO_OPERATION_ERROR}</li>
 *  <li>{@link #BIRTHDAY_DOES_NOT_MATCH}</li>
 */
public enum Errors {
    WRONG_USERNAME("Wrong username."),
    NAME_DOES_NOT_MATCH("Name should contains at least 2 letter."),
    SURNAME_DOES_NOT_MATCH("Surname should contains at least 2 letter."),
    PHONE_NUMBER_DOES_NOT_MATCH("Phone number should contains of '+' and 12 digits."),
    PASSWORD_DOES_NOT_MATCH("Password should contains at least 8 characters."),
    USER_ALREADY_EXISTS("User with such username already exists."),
    USER_DOES_NOT_EXIST("User with such username doesn't exist."),
    ACCOUNT_NUMBER_ERROR("Account number should be more than 0."),
    CREDIT_ERROR("Credit already requested"),
    AMOUNT_ERROR("Amount should be more than 0."),
    NOT_ENOUGH_ERROR("Not enough money."),
    ACCOUNT_ERROR("Account number doesn't exist."),
    VALIDITY_ERROR("Validity of your account is expired."),
    NO_REQUESTS_ERROR("There are no requests."),
    NO_REFILL_OPERATION_ERROR("There are no refill operations."),
    NO_OPERATION_ERROR("There are no operations."),
    BIRTHDAY_DOES_NOT_MATCH("You can register if you are over 18 years old only.");

    private String name;

    /**
     * Sole constructor. It is not possible to invoke this constructor.
     * It is for use by code emitted by the compiler in response to enum type declarations.
     * @param name The name of enum constant, which is the identifier used to declare it.
     */
    Errors(String name) {
        this.name = name;
    }

    /**
     * Gets the value of {@link #name}.
     *
     * @return the value of {@link #name}.
     */
    public String getName() {
        return name;
    }
}
