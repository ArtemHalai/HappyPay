package dao.mappers;

import model.TransferOperation;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class TransferMapper implements Mapper<TransferOperation> {

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
