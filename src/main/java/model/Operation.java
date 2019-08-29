package model;

import java.sql.Date;
import java.util.Objects;

public abstract class Operation {

    protected int userId;
    protected Date operationDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        Operation operation = (Operation) o;
        return userId == operation.userId &&
                operationDate.equals(operation.operationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, operationDate);
    }
}
