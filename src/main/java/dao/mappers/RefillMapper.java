package dao.mappers;


import model.RefillOperation;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class RefillMapper implements Mapper<RefillOperation> {


    @Override
    public RefillOperation getEntity(ResultSet resultSet) throws SQLException {

        RefillOperation refillOperation = new RefillOperation();

        refillOperation.setUserId(resultSet.getInt(USER_ID.getName()));
        refillOperation.setAmount(resultSet.getDouble(AMOUNT.getName()));
        refillOperation.setAccountNumber(resultSet.getString(ACCOUNT_NUMBER.getName()));
        refillOperation.setDate(resultSet.getDate(DATE.getName()));

        return refillOperation;
    }
}
