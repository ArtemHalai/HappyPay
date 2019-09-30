package dao.mappers;

import model.TransferOperation;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting transfer operation data from result set.
 */
public class TransferMapper implements Mapper<TransferOperation> {

    /**
     * Method to get transfer operation entity from result set.
     *
     * @param resultSet The result set object.
     * @return The TransferOperation object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see TransferOperation
     */
    @Override
    public TransferOperation getEntity(ResultSet resultSet) throws SQLException {
        TransferOperation transferOperation = new TransferOperation();
        transferOperation.setUserId(resultSet.getInt(USER_ID.getName()));
        transferOperation.setRecipientAccountNumber(resultSet.getLong(RECIPIENT_ACCOUNT_NUMBER.getName()));
        transferOperation.setAmount(resultSet.getDouble(AMOUNT.getName()));
        transferOperation.setDate(resultSet.getDate(DATE.getName()));

        return transferOperation;
    }
}
