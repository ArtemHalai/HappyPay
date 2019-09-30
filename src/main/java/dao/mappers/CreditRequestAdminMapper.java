package dao.mappers;

import model.CreditRequestAdmin;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting credit request admin data from result set.
 */
public class CreditRequestAdminMapper implements Mapper<CreditRequestAdmin> {

    /**
     * Method to get credit request admin entity from result set.
     *
     * @param resultSet The result set object.
     * @return The CreditRequestAdmin object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see CreditRequestAdmin
     */
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
