package dao.mappers;

import model.UserAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class UserAccountMapper implements Mapper<UserAccount> {


    @Override
    public UserAccount getEntity(ResultSet resultSet) throws SQLException {

        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(resultSet.getInt(USER_ID.getName()));
        userAccount.setBalance(resultSet.getInt(BALANCE.getName()));
        userAccount.setAccountNumber(resultSet.getLong(ACCOUNT_NUMBER.getName()));
        userAccount.setValidity(resultSet.getDate(VALIDITY.getName()));
        userAccount.setDeposit(resultSet.getBoolean(DEPOSIT.getName()));
        userAccount.setCredit(resultSet.getBoolean(CREDIT.getName()));

        return userAccount;
    }
}