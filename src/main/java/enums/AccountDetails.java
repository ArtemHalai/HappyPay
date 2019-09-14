package enums;

public enum AccountDetails {
    BALANCE(0),
    RATE(13.3),
    CREDIT_RATE(11.3);

    private double details;

    AccountDetails(double details) {
        this.details = details;
    }

    public double getDetails() {
        return details;
    }
}
