package model;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
public class LimitRequest {
    private int userId;
    private double amount;
    private Date operationDate;
    private boolean decision;
}
