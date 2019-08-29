package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserAccount extends Account {

    private Date validity;
    private boolean deposit;
    private boolean credit;
    private List<Operation> list;

    public UserAccount() {
        this.balance = 0.0;
        this.currency = "$";
        this.deposit = false;
        this.credit = false;
        this.list = new ArrayList<>();
    }

    public List<Operation> getList() {
        return list;
    }

    public void setList(List<Operation> list) {
        this.list = list;
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
        if (!(o instanceof UserAccount)) return false;
        if (!super.equals(o)) return false;
        UserAccount userAccount = (UserAccount) o;
        return Objects.equals(validity, userAccount.validity) &&
                deposit == userAccount.deposit &&
                credit == userAccount.credit && list.equals(userAccount.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), validity, deposit, credit, list);
    }
}
