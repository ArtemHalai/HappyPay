package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserAccount {
    protected int userId;
    private LocalDate validity;
    private double balance;
    private boolean deposit;
    private boolean credit;
    private long accountNumber;

    public UserAccount() {
        this.deposit = false;
        this.credit = false;
    }
}
