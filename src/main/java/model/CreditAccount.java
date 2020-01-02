package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static enums.Currency.USD;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreditAccount extends UserAccount {
    private double limit;
    private double rate;
    private double arrears;
    private double interestCharges;
    private String currency;

    public CreditAccount() {
        this.currency = USD.getName();
    }
}
