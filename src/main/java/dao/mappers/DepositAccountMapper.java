package dao.mappers;

import enums.DepositEnum;
import model.DepositAccount;
import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting deposit account data from result set.
 */
public class DepositAccountMapper implements Mapper<DepositAccount> {

    /**
     * Method to get deposit account entity from result set.
     *
     * @param resultSet The result set object.
     * @return The DepositAccount object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see DepositAccount
     */
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
