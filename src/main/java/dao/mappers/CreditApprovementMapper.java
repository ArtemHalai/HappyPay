package dao.mappers;

import model.CreditApprovementOperation;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class CreditApprovementMapper implements Mapper<CreditApprovementOperation> {


    @Override
    public CreditApprovementOperation getEntity(ResultSet resultSet) throws SQLException {

        CreditApprovementOperation creditApprovementOperation = new CreditApprovementOperation();
        creditApprovementOperation.setUserId(resultSet.getInt(USER_ID.getName()));
        creditApprovementOperation.setManagerId(resultSet.getInt(MANAGER_ID.getName()));
        creditApprovementOperation.setDate(resultSet.getDate(OPERATION_DATE.getName()));
        creditApprovementOperation.setDecision(resultSet.getBoolean(DECISION.getName()));

        return creditApprovementOperation;
    }
}
