package dao.mappers;

import enums.DepositEnum;
import model.DepositAccount;
import model.RefillOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static enums.Fields.*;

public class DepositAccountMapper implements Mapper<DepositAccount> {


    @Override
    public DepositAccount getEntity(ResultSet resultSet) throws SQLException {

        DepositAccount depositAccount = new DepositAccount();

        depositAccount.setUserId(resultSet.getInt(USER_ID.getName()));
        depositAccount.setBalance(resultSet.getDouble(BALANCE.getName()));
        depositAccount.setCurrency(resultSet.getString(CURRENCY.getName()));
        depositAccount.setStartDate(resultSet.getDate(START_DATE.getName()));
        depositAccount.setDepositEnum(DepositEnum.valueOf(resultSet.getString(DEPOSIT_TERM.getName())));
        depositAccount.setAccountNumber(resultSet.getLong(ACCOUNT_NUMBER.getName()));
        depositAccount.setRate(resultSet.getDouble(RATE.getName()));
        depositAccount.setTerm(resultSet.getDate(TERM.getName()));

        return depositAccount;
    }
}
