package dao.mappers;

import model.LimitRequest;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class LimiRequestMapper implements Mapper<LimitRequest> {

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
