package enums;

/**
 *  DAO type that can be used
 *  <li>{@link #BILL_PAYMENT_JDBC}</li>
 *  <li>{@link #CLIENT_DETAILS_JDBC}</li>
 *  <li>{@link #CREDIT_ACCOUNT_JDBC}</li>
 *  <li>{@link #CREDIT_APPROVEMENT_JDBC}</li>
 *  <li>{@link #DEPOSIT_ACCOUNT_JDBC}</li>
 *  <li>{@link #REFILL_JDBC}</li>
 *  <li>{@link #TRANSFER_JDBC}</li>
 *  <li>{@link #USER_ACCOUNT_JDBC}</li>
 *  <li>{@link #USER_JDBC}</li>
 *  <li>{@link #LIMIT_JDBC}</li>
 */
public enum DAOEnum {
    BILL_PAYMENT_JDBC("bill_payment_jdbc"),
    CLIENT_DETAILS_JDBC("client_details_jdbc"),
    CREDIT_ACCOUNT_JDBC("credit_account_jdbc"),
    CREDIT_APPROVEMENT_JDBC("credit_approvement_jdbc"),
    DEPOSIT_ACCOUNT_JDBC("deposit_account_jdbc"),
    REFILL_JDBC("refill_jdbc"),
    TRANSFER_JDBC("transfer_jdbc"),
    USER_ACCOUNT_JDBC("user_account_jdbc"),
    USER_JDBC("user_jdbc"),
    LIMIT_JDBC("limit_jdbc");

    private String name;

    /**
     * Sole constructor. It is not possible to invoke this constructor.
     * It is for use by code emitted by the compiler in response to enum type declarations.
     * @param name The name of enum constant, which is the identifier used to declare it.
     */
    DAOEnum(String name) {
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
