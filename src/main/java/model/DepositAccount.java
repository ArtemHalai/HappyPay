package model;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class DepositAccount extends Account {

    private double rate;
    private Date term;
    private List<RefillOperation> refillList;

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
        if (!(o instanceof DepositAccount)) return false;
        if (!super.equals(o)) return false;
        DepositAccount depositAccount = (DepositAccount) o;
        return Double.compare(depositAccount.rate, rate) == 0 &&
                term.equals(depositAccount.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rate, term);
    }
}
