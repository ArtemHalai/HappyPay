package dao.jdbc;

import dao.intefaces.UserDAO;
import dao.mappers.Mapper;
import dao.mappers.UserMapper;
import lombok.extern.log4j.Log4j;
import model.User;

import java.sql.*;

@Log4j
public class UserJDBC implements UserDAO {

    private Connection connection;

    public UserJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User isUserExist(String username) {
        Mapper<User> userMapper = new UserMapper();
        User obj = null;
        String isUserExist = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(isUserExist)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                obj = userMapper.getEntity(rs);
        } catch (SQLException e) {
            log.error("SQLException occurred in UserJDBC.class at isUserExist() method");
        }
        return obj;
    }

    @Override
    public int add(User user) {
        String addUser = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        int userId = -1;
        try (PreparedStatement statement = connection.prepareStatement(addUser, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                userId = rs.getInt(1);
        } catch (SQLException e) {
            log.error("SQLException occurred in UserJDBC.class from add() method");
        }
        return userId;
    }

    @Override
    public User getById(int id) {
        Mapper<User> userMapper = new UserMapper();
        User user = new User();
        user.setUserId(-1);

        String getById = "SELECT * FROM users WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next())
                user = userMapper.getEntity(rs);
        } catch (SQLException e) {
            log.error("SQLException occurred in UserJDBC.class at getById() method");
        }
        return user;
    }
}