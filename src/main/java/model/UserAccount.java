package model;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents a UserAccount object.
 */
public class UserAccount {

    protected int userId;
    private Date validity;
    private double balance;
    private boolean deposit;
    private boolean credit;
    private long accountNumber;

    /**
     * Creates a UserAccount object without params.
     */
    public UserAccount() {
        this.deposit = false;
        this.credit = false;
    }

    /**
     * Gets the value of {@link #balance}.
     *
     * @return the value of {@link #balance}.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * This is a setter which sets the balance.
     *
     * @param balance - the balance to be set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Gets the value of {@link #userId}.
     *
     * @return the value of {@link #userId}.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This is a setter which sets the user id.
     *
     * @param userId - the user id to be set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the value of {@link #accountNumber}.
     *
     * @return the value of {@link #accountNumber}.
     */
    public long getAccountNumber() {
        return accountNumber;
    }

    /**
     * This is a setter which sets the account number.
     *
     * @param accountNumber - the account number to be set
     */
    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gets the value of {@link #validity}.
     *
     * @return the value of {@link #validity}.
     */
    public Date getValidity() {
        return validity;
    }

    /**
     * This is a setter which sets the validity.
     *
     * @param validity - the validity to be set
     */
    public void setValidity(Date validity) {
        this.validity = validity;
    }

    /**
     * Gets the value of {@link #deposit}.
     *
     * @return the value of {@link #deposit}.
     */
    public boolean getDeposit() {
        return deposit;
    }

    /**
     * This is a setter which sets the deposit.
     *
     * @param deposit - the deposit to be set
     */
    public void setDeposit(boolean deposit) {
        this.deposit = deposit;
    }

    /**
     * Gets the value of {@link #credit}.
     *
     * @return the value of {@link #credit}.
     */
    public boolean getCredit() {
        return credit;
    }

    /**
     * This is a setter which sets the credit.
     *
     * @param credit - the credit to be set
     */
    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return userId == that.userId &&
                Double.compare(that.balance, balance) == 0 &&
                deposit == that.deposit &&
                credit == that.credit &&
                accountNumber == that.accountNumber &&
                Objects.equals(validity, that.validity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, validity, balance, deposit, credit, accountNumber);
    }
}
