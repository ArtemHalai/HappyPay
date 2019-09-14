package dao.mappers;

import model.BillPaymentOperation;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class BillPaymentMapper implements Mapper<BillPaymentOperation> {


    @Override
    public BillPaymentOperation getEntity(ResultSet resultSet) throws SQLException {

        BillPaymentOperation billPaymentOperation = new BillPaymentOperation();
        billPaymentOperation.setUserId(resultSet.getInt(USER_ID.getName()));
        billPaymentOperation.setBillNumber(resultSet.getLong(BILL_NUMBER.getName()));
        billPaymentOperation.setPurpose(resultSet.getString(PURPOSE.getName()));
        billPaymentOperation.setAmount(resultSet.getDouble(AMOUNT.getName()));
        billPaymentOperation.setDate(resultSet.getDate(DATE.getName()));

        return billPaymentOperation;
    }
}
