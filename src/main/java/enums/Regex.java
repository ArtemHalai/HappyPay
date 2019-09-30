package enums;

/**
 *  Regular expression that can be used
 *  <li>{@link #NAME_REGEX}</li>
 *  <li>{@link #USERNAME_REGEX}</li>
 *  <li>{@link #PHONE_NUMBER_REGEX}</li>
 *  <li>{@link #PASSWORD_REGEX}</li>
 *  <li>{@link #ACCOUNT_NUMBER_REGEX}</li>
 */
public enum Regex {
    NAME_REGEX("^[a-zA-Zа-яА-Я]{2,}$"),
    USERNAME_REGEX("^[a-zA-Z0-9]{5,}$"),
    PHONE_NUMBER_REGEX("^\\+\\d{12}$"),
    PASSWORD_REGEX("^.{8,}$"),
    ACCOUNT_NUMBER_REGEX("^\\d{16,30}$");

    private String name;

    /**
     * Sole constructor. It is not possible to invoke this constructor.
     * It is for use by code emitted by the compiler in response to enum type declarations.
     * @param name The name of enum constant, which is the identifier used to declare it.
     */
    Regex(String name) {
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
