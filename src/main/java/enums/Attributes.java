package enums;

/**
 *  Attributes that can be used
 *  <li>{@link #ERRORS}</li>
 *  <li>{@link #PAGE}</li>
 *  <li>{@link #USER}</li>
 *  <li>{@link #COUNT}</li>
 *  <li>{@link #PAGE_SIZE}</li>
 *  <li>{@link #NAME}</li>
 *  <li>{@link #ROOT}</li>
 *  <li>{@link #SURNAME}</li>
 *  <li>{@link #USERNAME}</li>
 *  <li>{@link #PASSWORD}</li>
 *  <li>{@link #PHONE_NUMBER}</li>
 *  <li>{@link #BIRTHDAY}</li>
 *  <li>{@link #ACCOUNT}</li>
 *  <li>{@link #TOTAL}</li>
 *  <li>{@link #NOT_ENOUGH_AMOUNT}</li>
 */
public enum Attributes {
    ERRORS("errors"),
    USER("user"),
    PAGE("page"),
    COUNT("count"),
    PAGE_SIZE("10"),
    NAME("name"),
    ROOT("root"),
    SURNAME("surname"),
    USERNAME("username"),
    PASSWORD("password"),
    PHONE_NUMBER("phone_number"),
    BIRTHDAY("birthday"),
    ACCOUNT("account"),
    TOTAL("total"),
    NOT_ENOUGH_AMOUNT("not_enough_amount");

    private String name;

    /**
     * Sole constructor. It is not possible to invoke this constructor.
     * It is for use by code emitted by the compiler in response to enum type declarations.
     * @param name The name of enum constant, which is the identifier used to declare it.
     */
    Attributes(String name) {
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
