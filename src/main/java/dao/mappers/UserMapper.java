package dao.mappers;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting user data from result set.
 */
public class UserMapper implements Mapper<User> {

    /**
     * Method to get user entity from result set.
     *
     * @param resultSet The result set object.
     * @return The User object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see User
     */
    @Override
    public User getEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt(USER_ID.getName()));
        user.setUsername(resultSet.getString(USERNAME.getName()));
        user.setPassword(resultSet.getString(PASSWORD.getName()));
        user.setRole(resultSet.getInt(ROLE.getName()));

        return user;
    }
}
