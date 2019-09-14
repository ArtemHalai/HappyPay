package enums;

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

    Fields(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
