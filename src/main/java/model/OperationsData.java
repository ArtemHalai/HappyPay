package model;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
public class OperationsData {
    private int userId;
    private double amount;
    private Date date;
    private String operationType;
}
