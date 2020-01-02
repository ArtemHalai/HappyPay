package model;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
public class LimitRequestAdmin {
    private int userId;
    private Date term;
    private double balance;
    private double amount;
    private boolean decision;
    private double arrears;
}
