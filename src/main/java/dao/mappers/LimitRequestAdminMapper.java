package dao.mappers;

import model.LimitRequestAdmin;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting limit request admin data from result set.
 */
public class LimitRequestAdminMapper implements Mapper<LimitRequestAdmin> {

    /**
     * Method to get limit request admin entity from result set.
     *
     * @param resultSet The result set object.
     * @return The LimitRequestAdmin object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see LimitRequestAdmin
     */
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
