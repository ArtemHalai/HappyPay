package dao.jdbc;

import dao.intefaces.UserDAO;
import dao.mappers.Mapper;
import dao.mappers.UserMapper;
import exceptions.WrongUsernameOrPasswordException;
import model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static enums.Fields.USER_ID;


public class UserJDBC implements UserDAO {

    private static final Logger LOG = Logger.getLogger(UserJDBC.class);
    private Connection connection;

    public UserJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isUserExist(String username) {
        String isUserExist = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(isUserExist)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserJDBC.class at isUserExist() method");
        }
        return false;
    }

    public int getUserIdByUsername(String username) {
        String getIdByUsername = "SELECT user_id FROM users WHERE username = ?";
        int id = -1;
        try (PreparedStatement statement = connection.prepareStatement(getIdByUsername)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                id = rs.getInt(USER_ID.getName());
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserJDBC.class at getUserIdByUsername() method");
        }
        return id;

    }

    @Override
    public User getUserByUsernameAndPassword(User user) {

        String getByUsernameAndPassword = "SELECT * FROM users WHERE username = ? AND password = ?";
        Mapper<User> userMapper = new UserMapper();
        User obj = null;

        try (PreparedStatement statement = connection.prepareStatement(getByUsernameAndPassword)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            ResultSet rs = statement.executeQuery();

            if (rs.next())
                obj = userMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserJDBC.class at getUserByUsernameAndPassword() method");
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
            LOG.error("SQLException occurred in UserJDBC.class from add() method");
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
            LOG.error("SQLException occurred in UserJDBC.class at getById() method");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();

        String findAll = "SELECT * FROM users";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            ResultSet rs = statement.executeQuery();

            Mapper<User> userMapper = new UserMapper();

            while (rs.next()) {
                User user = userMapper.getEntity(rs);
                list.add(user);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserJDBC.class at findAll() method");
        }
        return list;
    }

}