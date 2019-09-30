package dao.jdbc;

import dao.intefaces.UserDAO;
import dao.mappers.Mapper;
import dao.mappers.UserMapper;
import model.User;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Define a data access object used for executing user requests to database using JDBC.
 * This class is implementation of UserDAO.
 *
 * @see UserDAO
 */
public class UserJDBC implements UserDAO {

    private static final Logger LOG = Logger.getLogger(UserJDBC.class);
    private Connection connection;

    /**
     * Creates a UserJDBC object with the connection {@link #connection}.
     *
     * @param connection The connection object.
     */
    public UserJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method to get user by username.
     *
     * @param username The username of user.
     * @return The User object.
     * @see User
     */
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
            LOG.error("SQLException occurred in UserJDBC.class at isUserExist() method");
        }
        return obj;
    }

    /**
     * Method to get user by username and password.
     *
     * @param user The User object.
     * @return The User object.
     * @see User
     */
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

    /**
     * Method to add user.
     *
     * @param user The User object.
     * @return The int value representing the auto-generated id in database.
     * @see User
     */
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

    /**
     * Method to get user by id.
     *
     * @param id The user id.
     * @return The User object.
     * @see User
     */
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
}