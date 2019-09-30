package dao.mappers;

import model.OperationsData;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting operations data from result set.
 */
public class OperationMapper implements Mapper<OperationsData> {

    /**
     * Method to get operations data entity from result set.
     *
     * @param resultSet The result set object.
     * @return The OperationsData object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see OperationsData
     */
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
