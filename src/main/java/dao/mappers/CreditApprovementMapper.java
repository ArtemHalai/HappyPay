package dao.mappers;

import model.CreditApprovementOperation;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting credit approvement data from result set.
 */
public class CreditApprovementMapper implements Mapper<CreditApprovementOperation> {

    /**
     * Method to get credit approvement entity from result set.
     *
     * @param resultSet The result set object.
     * @return The CreditApprovementOperation object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see CreditApprovementOperation
     */
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
