package dao.mappers;

import model.UserAccount;
import util.SqlDateLocalDateTimeConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static enums.Fields.*;

public class UserAccountMapper implements Mapper<UserAccount> {

    @Override
    public UserAccount getEntity(ResultSet resultSet) throws SQLException {
        UserAccount userAccount = new UserAccount();

        userAccount.setUserId(resultSet.getInt(USER_ID.getName()));
        userAccount.setBalance(resultSet.getDouble(BALANCE.getName()));
        userAccount.setAccountNumber(resultSet.getLong(ACCOUNT_NUMBER.getName()));
        LocalDateTime localDateTime = SqlDateLocalDateTimeConverter.convertSqlDateToLocalDateTime(resultSet.getDate(VALIDITY.getName()));
        userAccount.setValidity(localDateTime);
        userAccount.setDeposit(resultSet.getBoolean(DEPOSIT.getName()));
        userAccount.setCredit(resultSet.getBoolean(CREDIT.getName()));

        return userAccount;
    }
}