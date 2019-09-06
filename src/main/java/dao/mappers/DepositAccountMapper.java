package dao.mappers;

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
        depositAccount.setIban(resultSet.getLong(IBAN.getName()));
        depositAccount.setRate(resultSet.getDouble(RATE.getName()));
        depositAccount.setTerm(resultSet.getDate(TERM.getName()));
        List<RefillOperation> list = new ArrayList<>();

        while (resultSet.next()) {
            RefillOperation refillOperation = new RefillOperation();
            refillOperation.setUserId(resultSet.getInt(USER_ID.getName()));
            refillOperation.setDate(resultSet.getDate(DATE.getName()));
            refillOperation.setAmount(resultSet.getDouble(AMOUNT.getName()));
            refillOperation.setAccountNumber(resultSet.getLong(ACCOUNT_NUMBER.getName()));
            refillOperation.setSenderIBAN(resultSet.getLong(SENDER_IBAN.getName()));
            list.add(refillOperation);
        }
        depositAccount.setRefillList(list);

        return depositAccount;
    }
}
