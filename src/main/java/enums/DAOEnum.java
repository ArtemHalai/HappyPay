package enums;

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

    DAOEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
