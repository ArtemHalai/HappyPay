package enums;

public enum OperationType {

    BILL_PAYMENT("BILL PAYMENT"),
    TRANSFER_OPERATION("TRANSFER OPERATION"),
    REFILL_OPERATION("REFILL OPERATION"),
    REFILL_DEPOSIT("REFILL DEPOSIT"),
    CREDIT_APPROVEMENT("CREDIT APPROVEMENT");

    private String name;

    OperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
