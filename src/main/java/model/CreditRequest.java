package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class CreditRequest {
    private int userId;
    private double amount;
    private boolean decision;
    private Date operationDate;
}
