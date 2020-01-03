package dao.mappers;

import model.LimitRequestAdmin;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class LimitRequestAdminMapper implements Mapper<LimitRequestAdmin> {

    @Override
    public LimitRequestAdmin getEntity(ResultSet resultSet) throws SQLException {
        LimitRequestAdmin limitRequestAdmin = new LimitRequestAdmin();

        limitRequestAdmin.setUserId(resultSet.getInt(USER_ID.getName()));
        limitRequestAdmin.setAmount(resultSet.getDouble(AMOUNT.getName()));
        limitRequestAdmin.setTerm(resultSet.getDate(VALIDITY.getName()));
        limitRequestAdmin.setBalance(resultSet.getDouble(BALANCE.getName()));
        limitRequestAdmin.setDecision(resultSet.getBoolean(DECISION.getName()));
        limitRequestAdmin.setArrears(resultSet.getDouble(ARREARS.getName()));

        return limitRequestAdmin;
    }
}
