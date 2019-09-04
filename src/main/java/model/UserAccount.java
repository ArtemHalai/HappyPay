package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserAccount {

    protected int userId;
    private Date validity;
    private boolean deposit;
    private boolean credit;
    private long accountNumber;

    public UserAccount() {
        this.deposit = false;
        this.credit = false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public boolean getDeposit() {
        return deposit;
    }

    public void setDeposit(boolean deposit) {
        this.deposit = deposit;
    }

    public boolean getCredit() {
        return credit;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return userId == that.userId &&
                deposit == that.deposit &&
                credit == that.credit &&
                Objects.equals(validity, that.validity) &&
                Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, validity, deposit, credit, accountNumber);
    }
}
