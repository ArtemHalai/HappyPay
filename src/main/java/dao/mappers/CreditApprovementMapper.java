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
        creditApprovementOperation.setDate(resultSet.getDate(DATE.getName()));
        creditApprovementOperation.setOperationType(resultSet.getString(OPERATION_TYPE.getName()));
        creditApprovementOperation.setDecision(resultSet.getBoolean(DECISION.getName()));

        return creditApprovementOperation;
    }
}
