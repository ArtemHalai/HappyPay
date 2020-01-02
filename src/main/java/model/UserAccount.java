package model;

import lombok.Data;

import java.sql.Date;

@Data
public class UserAccount {
    protected int userId;
    private Date validity;
    private double balance;
    private boolean deposit;
    private boolean credit;
    private long accountNumber;

    public UserAccount() {
        this.deposit = false;
        this.credit = false;
    }
}
