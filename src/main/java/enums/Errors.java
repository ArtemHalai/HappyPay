package enums;

public enum Errors {
    WRONG_USERNAME("Wrong username."),
    NAME_DOES_NOT_MATCH("Name should contains at least 2 letter."),
    SURNAME_DOES_NOT_MATCH("Surname should contains at least 2 letter."),
    PHONE_NUMBER_DOES_NOT_MATCH("Phone number should contains of '+' and 12 digits."),
    PASSWORD_DOES_NOT_MATCH("Password should contains at least 8 characters."),
    USER_ALREADY_EXISTS("User with such username already exists."),
    USER_DOES_NOT_EXIST("User with such username doesn't exist."),
    ACCOUNT_NUMBER_ERROR("Account number should contains 16 - 30 digits."),
    SOMETHING_WRONG("Something was wrong."),
    AMOUNT_ERROR("Amount should be more than 0."),
    BIRTHDAY_DOES_NOT_MATCH("You can register if you are over 18 years old only.");

    private String name;

    Errors(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
