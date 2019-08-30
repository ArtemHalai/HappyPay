package enums;

public enum ServiceEnum {

    BILL_PAYMENT_SERVICE("bill_payment_service"),
    CLIENT_DETAILS_SERVICE("client_details_service"),
    CREDIT_ACCOUNT_SERVICE("credit_account_service"),
    CREDIT_APPROVEMENT_SERVICE("credit_approvement_service"),
    DEPOSIT_ACCOUNT_SERVICE("deposit_account_service"),
    REFILL_SERVICE("refill_service"),
    TRANSFER_SERVICE("transfer_service"),
    USER_ACCOUNT_SERVICE("user_account_service"),
    USER_SERVICE("user_service");

    private String name;

    ServiceEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
