package dao.mappers;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;


public class UserMapper implements Mapper<User> {

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
