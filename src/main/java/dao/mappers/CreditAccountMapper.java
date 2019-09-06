package dao.mappers;

import model.CreditAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class CreditAccountMapper implements Mapper<CreditAccount> {


    @Override
    public CreditAccount getEntity(ResultSet resultSet) throws SQLException {

        CreditAccount creditAccount = new CreditAccount();
        creditAccount.setUserId(resultSet.getInt(USER_ID.getName()));
        creditAccount.setBalance(resultSet.getDouble(BALANCE.getName()));
        creditAccount.setCurrency(resultSet.getString(CURRENCY.getName()));
        creditAccount.setIban(resultSet.getLong(IBAN.getName()));
        creditAccount.setLimit(resultSet.getDouble(LIMIT.getName()));
        creditAccount.setRate(resultSet.getDouble(RATE.getName()));
        creditAccount.setArrears(resultSet.getDouble(ARREARS.getName()));
        creditAccount.setInterestCharges(resultSet.getDouble(INTEREST_CHARGES.getName()));

        return creditAccount;
    }
}
