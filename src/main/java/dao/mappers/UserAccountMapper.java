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
        userAccount.setBalance(resultSet.getFloat(BALANCE.getName()));
        userAccount.setCurrency(resultSet.getString(CURRENCY.getName()));
        userAccount.setValidity(resultSet.getDate(VALIDITY.getName()));
        userAccount.setDeposit(resultSet.getBoolean(DEPOSIT.getName()));
        userAccount.setCredit(resultSet.getBoolean(CREDIT.getName()));

        return userAccount;
    }
}