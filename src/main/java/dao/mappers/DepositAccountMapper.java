package dao.mappers;

import model.DepositAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class DepositAccountMapper implements Mapper<DepositAccount> {


    @Override
    public DepositAccount getEntity(ResultSet resultSet) throws SQLException {

        DepositAccount depositAccount = new DepositAccount();

        depositAccount.setUserId(resultSet.getInt(USER_ID.getName()));
        depositAccount.setBalance(resultSet.getDouble(BALANCE.getName()));
        depositAccount.setCurrency(resultSet.getString(CURRENCY.getName()));
        depositAccount.setRate(resultSet.getDouble(RATE.getName()));
        depositAccount.setTerm(resultSet.getDate(TERM.getName()));

        return depositAccount;
    }
}
