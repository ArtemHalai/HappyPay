package enums;

/**
 *  Account type that can be used
 *  <li>{@link #DEPOSIT_TYPE}</li>
 *  <li>{@link #MAIN_ACCOUNT}</li>
 *  <li>{@link #CREDIT_TYPE}</li>
 */
public enum AccountType {
    DEPOSIT_TYPE("deposit"),
    MAIN_ACCOUNT("main_account"),
    CREDIT_TYPE("credit");

    private String name;

    /**
     * Gets the value of {@link #name}.
     *
     * @return the value of {@link #name}.
     */
    public String getName() {
        return name;
    }

    /**
     * Sole constructor. It is not possible to invoke this constructor.
     * It is for use by code emitted by the compiler in response to enum type declarations.
     * @param name The name of enum constant, which is the identifier used to declare it.
     */
    AccountType(String name) {
        this.name = name;
    }
}
