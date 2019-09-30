package dao.jdbc;

import dao.intefaces.DepositAccountDAO;
import dao.mappers.DepositAccountMapper;
import dao.mappers.Mapper;
import model.DepositAccount;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static enums.Attributes.TOTAL;

/**
 * Define a data access object used for executing deposit account's requests to database using JDBC.
 * This class is implementation of DepositAccountDAO.
 *
 * @see DepositAccountDAO
 */
public class DepositAccountJDBC implements DepositAccountDAO {

    private static final Logger LOG = Logger.getLogger(DepositAccountJDBC.class);
    private Connection connection;

    /**
     * Creates a DepositAccountJDBC object with the connection {@link #connection}.
     *
     * @param connection The connection object.
     */
    public DepositAccountJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method to add deposit account.
     *
     * @param depositAccount The DepositAccount object.
     * @return <code>true</code> if deposit account was added; <code>false</code> otherwise.
     * @see DepositAccount
     */
    @Override
    public boolean add(DepositAccount depositAccount) {

        String add = "INSERT INTO deposit_accounts (user_id, balance, currency, deposit_term, rate, term, start_date)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add)) {
            statement.setInt(1, depositAccount.getUserId());
            statement.setDouble(2, depositAccount.getBalance());
            statement.setString(3, depositAccount.getCurrency());
            statement.setString(4, depositAccount.getDepositEnum().getName());
            statement.setDouble(5, depositAccount.getRate());
            statement.setDate(6, depositAccount.getTerm(), Calendar.getInstance());
            statement.setDate(7, depositAccount.getStartDate(), Calendar.getInstance());
            int generated = statement.executeUpdate();

            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at add() method");
        }
        return false;
    }

    /**
     * Method to update balance of deposit account by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if balance was updated; <code>false</code> otherwise.
     */
    @Override
    public boolean updateBalanceById(double amount, int userId) {
        String updateBalance = "UPDATE deposit_accounts SET balance = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateBalance)) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at updateBalanceById() method");
        }
        return false;
    }

    /**
     * Method to delete deposit account by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if deposit account was deleted; <code>false</code> otherwise.
     */
    @Override
    public boolean deleteDepositAccount(int userId) {
        if (count(userId) > 0) {
            String delete = "DELETE FROM deposit_accounts WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(delete)) {
                statement.setInt(1, userId);
                int generated = statement.executeUpdate();
                if (generated > 0)
                    return true;
            } catch (SQLException e) {
                LOG.error("SQLException occurred in DepositAccountJDBC.class at deleteDepositAccount() method");
            }
            return false;
        }
        return true;
    }

    /**
     * Method to get count of all deposit accounts in database by user id.
     *
     * @param userId The user id.
     * @return The int value representing amount of all deposit accounts in database by given user id.
     */
    @Override
    public int count(int userId) {
        String count = "SELECT COUNT(*) AS total FROM deposit_accounts WHERE user_id = ?";
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(count)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                total = rs.getInt(TOTAL.getName());
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at count() method");
        }
        return total;
    }

    /**
     * Method to get all deposit accounts.
     *
     * @return The list containing all deposit accounts.
     * @see DepositAccount
     */
    @Override
    public List<DepositAccount> getAll() {
        List<DepositAccount> list = new ArrayList<>();

        String findAll = "SELECT * FROM deposit_accounts";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            ResultSet rs = statement.executeQuery();
            Mapper<DepositAccount> depositAccountMapper = new DepositAccountMapper();
            while (rs.next()) {
                DepositAccount depositAccount = depositAccountMapper.getEntity(rs);
                list.add(depositAccount);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at getAll() method");
        }
        return list;
    }

    /**
     * Method to get deposit account by id.
     *
     * @param id The user id.
     * @return The DepositAccount object.
     * @see DepositAccount
     */
    @Override
    public DepositAccount getById(int id) {
        Mapper<DepositAccount> depositAccountMapper = new DepositAccountMapper();
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setUserId(-1);
        String getById = "SELECT * FROM deposit_accounts WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                depositAccount = depositAccountMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at getById() method");
        }
        return depositAccount;
    }
}
