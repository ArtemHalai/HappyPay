package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static enums.Currency.USD;

public class DepositAccount extends Account {

    private double rate;
    private Date term;
    private List<RefillOperation> refillList = new ArrayList<>();

    public DepositAccount() {
        this.currency = USD.getName();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DepositAccount account = (DepositAccount) o;
        return Double.compare(account.rate, rate) == 0 &&
                Objects.equals(term, account.term) &&
                Objects.equals(refillList, account.refillList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rate, term, refillList);
    }
}
