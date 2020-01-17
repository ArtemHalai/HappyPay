package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class CreditRequestAdmin {
    private int userId;
    private double balance;
    private Date validity;
    private double amount;
    private boolean decision;
}
