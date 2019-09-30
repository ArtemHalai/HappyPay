package enums;

/**
 *  Account details that can be used
 *  <li>{@link #BALANCE}</li>
 *  <li>{@link #RATE}</li>
 *  <li>{@link #CREDIT_RATE}</li>
 */
public enum AccountDetails {
    BALANCE(0),
    RATE(13.3),
    CREDIT_RATE(11.3);

    private double details;

    /**
     * Sole constructor. It is not possible to invoke this constructor.
     * It is for use by code emitted by the compiler in response to enum type declarations.
     * @param details The double value of enum constant, which is the identifier used to declare it.
     */
    AccountDetails(double details) {
        this.details = details;
    }

    /**
     * Gets the value of {@link #details}.
     *
     * @return the value of {@link #details}.
     */
    public double getDetails() {
        return details;
    }
}
