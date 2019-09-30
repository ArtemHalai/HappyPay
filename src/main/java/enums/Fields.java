package enums;

/**
 *  Fields that can be used
 *  <li>{@link #USER_ID}</li>
 *  <li>{@link #NAME}</li>
 *  <li>{@link #SURNAME}</li>
 *  <li>{@link #MOBILE_PHONE}</li>
 *  <li>{@link #USERNAME}</li>
 *  <li>{@link #PASSWORD}</li>
 *  <li>{@link #BIRTHDAY}</li>
 *  <li>{@link #BILL_NUMBER}</li>
 *  <li>{@link #ROLE}</li>
 *  <li>{@link #ADMIN_ID}</li>
 *  <li>{@link #PURPOSE}</li>
 *  <li>{@link #BALANCE}</li>
 *  <li>{@link #CURRENCY}</li>
 *  <li>{@link #START_DATE}</li>
 *  <li>{@link #DEPOSIT_TERM}</li>
 *  <li>{@link #ACCOUNT}</li>
 *  <li>{@link #VALIDITY}</li>
 *  <li>{@link #DEPOSIT}</li>
 *  <li>{@link #DECISION}</li>
 *  <li>{@link #CREDIT}</li>
 *  <li>{@link #AMOUNT}</li>
 *  <li>{@link #DATE}</li>
 *  <li>{@link #SENDER_ACCOUNT_TYPE}</li>
 *  <li>{@link #OPERATION_TYPE}</li>
 *  <li>{@link #OPERATION_DATE}</li>
 *  <li>{@link #RATE}</li>
 *  <li>{@link #LIMIT}</li>
 *  <li>{@link #ARREARS}</li>
 *  <li>{@link #INTEREST_CHARGES}</li>
 *  <li>{@link #TERM}</li>
 *  <li>{@link #ACCOUNT_NUMBER}</li>
 *  <li>{@link #RECIPIENT_ACCOUNT_NUMBER}</li>
 */
public enum Fields {
    USER_ID("user_id"),
    NAME("name"),
    SURNAME("surname"),
    MOBILE_PHONE("phone_number"),
    USERNAME("username"),
    PASSWORD("password"),
    BIRTHDAY("birthday"),
    BILL_NUMBER("bill_number"),
    ROLE("role"),
    ADMIN_ID("admin_id"),
    PURPOSE("purpose"),
    BALANCE("balance"),
    CURRENCY("currency"),
    START_DATE("start_date"),
    DEPOSIT_TERM("deposit_term"),
    ACCOUNT("account"),
    VALIDITY("validity"),
    DEPOSIT("deposit"),
    DECISION("decision"),
    CREDIT("credit"),
    AMOUNT("amount"),
    DATE("date"),
    SENDER_ACCOUNT_TYPE("sender_account_type"),
    OPERATION_TYPE("operation_type"),
    OPERATION_DATE("operation_date"),
    RATE("rate"),
    LIMIT("credit_limit"),
    ARREARS("arrears"),
    INTEREST_CHARGES("interest_charges"),
    TERM("term"),
    ACCOUNT_NUMBER("account_number"),
    RECIPIENT_ACCOUNT_NUMBER("recipient_account_number");

    private String name;

    /**
     * Sole constructor. It is not possible to invoke this constructor.
     * It is for use by code emitted by the compiler in response to enum type declarations.
     * @param name The name of enum constant, which is the identifier used to declare it.
     */
    Fields(String name) {
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
