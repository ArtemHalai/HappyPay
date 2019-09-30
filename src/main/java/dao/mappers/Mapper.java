package dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The object used for getting needed data from result set.
 */
public interface Mapper<T> {

    /**
     * Method to get necessary entity from result set.
     *
     * @param resultSet The result set object.
     * @return The typed object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     */
    T getEntity(ResultSet resultSet) throws SQLException;
}

