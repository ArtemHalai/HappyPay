package dao.mappers;

import model.OperationsData;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class OperationMapper implements Mapper<OperationsData> {

    @Override
    public OperationsData getEntity(ResultSet resultSet) throws SQLException {
        OperationsData operationsData = new OperationsData();
        operationsData.setUserId(resultSet.getInt(USER_ID.getName()));
        operationsData.setAmount(resultSet.getDouble(AMOUNT.getName()));
        operationsData.setDate(resultSet.getDate(DATE.getName()));
        operationsData.setOperationType(resultSet.getString(OPERATION_TYPE.getName()));

        return operationsData;
    }
}
