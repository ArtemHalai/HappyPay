package model;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
public abstract class Operation {
    protected int userId;
    protected Date date;
    protected double amount;
    protected String operationType;
}
