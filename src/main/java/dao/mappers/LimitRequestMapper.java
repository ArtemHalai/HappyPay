package dao.mappers;

import model.LimitRequest;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting limit request data from result set.
 */
public class LimitRequestMapper implements Mapper<LimitRequest> {

    /**
     * Method to get limit request entity from result set.
     *
     * @param resultSet The result set object.
     * @return The LimitRequest object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see LimitRequest
     */
    @Override
    public LimitRequest getEntity(ResultSet resultSet) throws SQLException {
        LimitRequest limitRequest = new LimitRequest();
        limitRequest.setUserId(resultSet.getInt(USER_ID.getName()));
        limitRequest.setAmount(resultSet.getDouble(AMOUNT.getName()));
        limitRequest.setDecision(resultSet.getBoolean(DECISION.getName()));
        limitRequest.setOperationDate(resultSet.getDate(OPERATION_DATE.getName()));

        return limitRequest;
    }
}
