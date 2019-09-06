package enums;

public enum Fields {

    USER_ID("user_id"),
    NAME("name"),
    SURNAME("surname"),
    MOBILE_PHONE("mobile_phone"),
    MANAGER_ID("manager_id"),
    USERNAME("username"),
    PASSWORD("password"),
    BIRTHDAY("birthday"),
    BILL_NUMBER("bill_number"),
    ROLE("role"),
    PURPOSE("purpose"),
    BALANCE("balance"),
    CURRENCY("currency"),
    IBAN("iban"),
    SENDER_IBAN("sender_iban"),
    VALIDITY("validity"),
    DEPOSIT("deposit"),
    DECISION("decision"),
    CREDIT("credit"),
    AMOUNT("amount"),
    DATE("date"),
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
