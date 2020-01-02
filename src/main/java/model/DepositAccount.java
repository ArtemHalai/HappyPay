package model;

import enums.DepositEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

import static enums.Currency.USD;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepositAccount extends UserAccount {
    private double rate;
    private Date startDate;
    private Date term;
    private String currency;
    private double balance;
    private DepositEnum depositEnum;

    public DepositAccount() {
        this.currency = USD.getName();
        this.startDate = new Date(System.currentTimeMillis());
    }
}
