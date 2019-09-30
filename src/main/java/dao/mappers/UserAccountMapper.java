package dao.mappers;

import model.UserAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting user account data from result set.
 */
public class UserAccountMapper implements Mapper<UserAccount> {

    /**
     * Method to get user account entity from result set.
     *
     * @param resultSet The result set object.
     * @return The UserAccount object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see UserAccount
     */
    @Override
    public UserAccount getEntity(ResultSet resultSet) throws SQLException {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(resultSet.getInt(USER_ID.getName()));
        userAccount.setBalance(resultSet.getDouble(BALANCE.getName()));
        userAccount.setAccountNumber(resultSet.getLong(ACCOUNT_NUMBER.getName()));
        userAccount.setValidity(resultSet.getDate(VALIDITY.getName()));
        userAccount.setDeposit(resultSet.getBoolean(DEPOSIT.getName()));
        userAccount.setCredit(resultSet.getBoolean(CREDIT.getName()));

        return userAccount;
    }
}