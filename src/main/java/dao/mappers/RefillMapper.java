package dao.mappers;

import model.RefillOperation;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting refill operation data from result set.
 */
public class RefillMapper implements Mapper<RefillOperation> {

    /**
     * Method to get refill operation entity from result set.
     *
     * @param resultSet The result set object.
     * @return The RefillOperation object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see RefillOperation
     */
    @Override
    public RefillOperation getEntity(ResultSet resultSet) throws SQLException {
        RefillOperation refillOperation = new RefillOperation();
        refillOperation.setUserId(resultSet.getInt(USER_ID.getName()));
        refillOperation.setAmount(resultSet.getDouble(AMOUNT.getName()));
        refillOperation.setDate(resultSet.getDate(DATE.getName()));
        refillOperation.setSenderAccountType(resultSet.getString(SENDER_ACCOUNT_TYPE.getName()));

        return refillOperation;
    }
}
