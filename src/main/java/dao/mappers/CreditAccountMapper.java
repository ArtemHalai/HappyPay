package dao.mappers;

import model.CreditAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting credit account data from result set.
 */
public class CreditAccountMapper implements Mapper<CreditAccount> {

    /**
     * Method to get credit account entity from result set.
     *
     * @param resultSet The result set object.
     * @return The CreditAccount object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see CreditAccount
     */
    @Override
    public CreditAccount getEntity(ResultSet resultSet) throws SQLException {
        CreditAccount creditAccount = new CreditAccount();
        creditAccount.setUserId(resultSet.getInt(USER_ID.getName()));
        creditAccount.setCurrency(resultSet.getString(CURRENCY.getName()));
        creditAccount.setAccountNumber(resultSet.getLong(ACCOUNT_NUMBER.getName()));
        creditAccount.setLimit(resultSet.getDouble(LIMIT.getName()));
        creditAccount.setRate(resultSet.getDouble(RATE.getName()));
        creditAccount.setArrears(resultSet.getDouble(ARREARS.getName()));
        creditAccount.setInterestCharges(resultSet.getDouble(INTEREST_CHARGES.getName()));

        return creditAccount;
    }
}
