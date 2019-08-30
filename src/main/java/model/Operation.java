package model;

import java.sql.Date;
import java.util.Objects;

public abstract class Operation {

    protected int userId;
    protected Date date;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        Operation operation = (Operation) o;
        return userId == operation.userId &&
                date.equals(operation.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, date);
    }
}
