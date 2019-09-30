package enums;

/**
 *  Operation type that can be used
 *  <li>{@link #BILL_PAYMENT}</li>
 *  <li>{@link #TRANSFER_OPERATION}</li>
 *  <li>{@link #REFILL_OPERATION}</li>
 *  <li>{@link #REFILL_DEPOSIT}</li>
 *  <li>{@link #CREDIT_APPROVEMENT}</li>
 */
public enum OperationType {
    BILL_PAYMENT("BILL PAYMENT"),
    TRANSFER_OPERATION("TRANSFER OPERATION"),
    REFILL_OPERATION("REFILL OPERATION"),
    REFILL_DEPOSIT("REFILL DEPOSIT"),
    CREDIT_APPROVEMENT("CREDIT APPROVEMENT");

    private String name;

    /**
     * Sole constructor. It is not possible to invoke this constructor.
     * It is for use by code emitted by the compiler in response to enum type declarations.
     * @param name The name of enum constant, which is the identifier used to declare it.
     */
    OperationType(String name) {
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
