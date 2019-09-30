package dao.jdbc;

import dao.intefaces.UserAccountDAO;
import dao.mappers.Mapper;
import dao.mappers.UserAccountMapper;
import model.UserAccount;
import org.apache.log4j.Logger;
import util.DateValidity;

import java.sql.*;
import java.util.Calendar;

/**
 * Define a data access object used for executing user account requests to database using JDBC.
 * This class is implementation of UserAccountDAO.
 *
 * @see UserAccountDAO
 */
public class UserAccountJDBC implements UserAccountDAO {

    private static final Logger LOG = Logger.getLogger(UserAccountJDBC.class);
    private Connection connection;

    /**
     * Creates a UserAccountJDBC object with the connection {@link #connection}.
     *
     * @param connection The connection object.
     */
    public UserAccountJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method to add user account.
     *
     * @param userAccount The UserAccount object.
     * @return <code>true</code> if user account was added; <code>false</code> otherwise.
     * @see UserAccount
     */
    @Override
    public boolean add(UserAccount userAccount) {

        String addUserAccount = "INSERT INTO user_account (user_id, validity, deposit, credit) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addUserAccount)) {
            statement.setInt(1, userAccount.getUserId());
            statement.setDate(2, userAccount.getValidity(), Calendar.getInstance());
            statement.setBoolean(3, userAccount.getDeposit());
            statement.setBoolean(4, userAccount.getCredit());
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at add() method");
        }
        return false;
    }

    /**
     * Method to update balance of account by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if balance was updated; <code>false</code> otherwise.
     */
    @Override
    public boolean updateBalanceById(double amount, int userId) {
        String updateBalance = "UPDATE user_account SET balance = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateBalance)) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at updateBalanceById() method");
        }
        return false;
    }

    /**
     * Method to get user account by id.
     *
     * @param id The user id.
     * @return The UserAccount object.
     * @see UserAccount
     */
    @Override
    public UserAccount getById(int id) {
        Mapper<UserAccount> userAccountMapper = new UserAccountMapper();
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(-1);

        String getById = "SELECT * FROM user_account WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next())
                userAccount = userAccountMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at getById() method");
        }
        return userAccount;
    }

    /**
     * Method to update credit status by user id.
     *
     * @param decision The decision.
     * @param id The user id.
     * @return <code>true</code> if status was updated; <code>false</code> otherwise.
     */
    @Override
    public boolean updateCreditStatusById(int id, boolean decision) {
        String updateStatus = "UPDATE user_account SET credit = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateStatus)) {
            statement.setBoolean(1, decision);
            statement.setInt(2, id);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at updateCreditStatusById() method");
        }
        return false;
    }

    /**
     * Method to update term of account by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if term was updated; <code>false</code> otherwise.
     */
    @Override
    public boolean updateTerm(int userId) {
        String updateTerm = "UPDATE user_account SET validity = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateTerm)) {
            statement.setDate(1, DateValidity.setValidity(), Calendar.getInstance());
            statement.setLong(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at updateTerm() method");
        }
        return false;
    }

    /**
     * Method to get user account by account number.
     *
     * @param recipientAccountNumber The recipient account number.
     * @return The user account object.
     * @see UserAccount
     */
    @Override
    public UserAccount getByAccountNumber(long recipientAccountNumber) {
        Mapper<UserAccount> userAccountMapper = new UserAccountMapper();
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(-1);

        String getById = "SELECT * FROM user_account WHERE account_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setLong(1, recipientAccountNumber);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                userAccount = userAccountMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at getByAccountNumber() method");
        }
        return userAccount;
    }

    /**
     * Method to update balance by account number.
     *
     * @param recipientAccountNumber The recipient account number to be updated.
     * @param amount The amount to set.
     * @return <code>true</code> if balance was updated; <code>false</code> otherwise.
     */
    @Override
    public boolean updateByAccount(double amount, long recipientAccountNumber) {
        String updateBalance = "UPDATE user_account SET balance = ? WHERE account_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateBalance)) {
            statement.setDouble(1, amount);
            statement.setLong(2, recipientAccountNumber);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at updateByAccount() method");
        }
        return false;
    }

    /**
     * Method to update deposit status by user id.
     *
     * @param decision The decision.
     * @param userId The user id.
     * @return <code>true</code> if status was updated; <code>false</code> otherwise.
     */
    @Override
    public boolean updateDepositStatusById(int userId, boolean decision) {
        String updateStatus = "UPDATE user_account SET deposit = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateStatus)) {
            statement.setBoolean(1, decision);
            statement.setInt(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at updateDepositStatusById() method");
        }
        return false;
    }
}
