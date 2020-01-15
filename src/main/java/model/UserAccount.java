package model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAccount {
    protected int userId;
    private LocalDateTime validity;
    private double balance;
    private boolean deposit;
    private boolean credit;
    private long accountNumber;

    public UserAccount() {
        this.deposit = false;
        this.credit = false;
    }
}
