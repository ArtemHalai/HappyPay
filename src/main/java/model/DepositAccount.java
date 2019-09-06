package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static enums.Currency.USD;

public class DepositAccount extends UserAccount {

    private double rate;
    private Date term;
    private String currency;
    private double balance;
    private long iban;
    private long accountNumber;
    private List<RefillOperation> refillList = new ArrayList<>();

    public DepositAccount() {
        this.currency = USD.getName();
    }

    public long getIban() {
        return iban;
    }

    public void setIban(long iban) {
        this.iban = iban;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getTerm() {
        return term;
    }

    public void setTerm(Date term) {
        this.term = term;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<RefillOperation> getRefillList() {
        return refillList;
    }

    public void setRefillList(List<RefillOperation> refillList) {
        this.refillList = refillList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DepositAccount that = (DepositAccount) o;
        return Double.compare(that.rate, rate) == 0 &&
                Double.compare(that.balance, balance) == 0 &&
                iban == that.iban &&
                accountNumber == that.accountNumber &&
                Objects.equals(term, that.term) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(refillList, that.refillList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rate, term, currency, balance, iban, accountNumber, refillList);
    }
}
