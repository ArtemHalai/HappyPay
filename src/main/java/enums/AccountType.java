package enums;

public enum AccountType {

    DEPOSIT_TYPE("deposit"),
    MAIN_ACCOUNT("main_account"),
    CREDIT_TYPE("credit");


    private String name;

    public String getName() {
        return name;
    }

    AccountType(String name) {
        this.name = name;
    }
}
