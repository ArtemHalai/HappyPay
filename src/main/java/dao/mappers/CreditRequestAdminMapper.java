package dao.mappers;

import model.CreditRequestAdmin;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class CreditRequestAdminMapper implements Mapper<CreditRequestAdmin> {

    @Override
    public CreditRequestAdmin getEntity(ResultSet resultSet) throws SQLException {
        CreditRequestAdmin creditRequestAdmin = new CreditRequestAdmin();

        creditRequestAdmin.setUserId(resultSet.getInt(USER_ID.getName()));
        creditRequestAdmin.setAmount(resultSet.getDouble(AMOUNT.getName()));
        creditRequestAdmin.setValidity(resultSet.getDate(VALIDITY.getName()));
        creditRequestAdmin.setBalance(resultSet.getDouble(BALANCE.getName()));
        creditRequestAdmin.setDecision(resultSet.getBoolean(DECISION.getName()));

        return creditRequestAdmin;
    }
}
