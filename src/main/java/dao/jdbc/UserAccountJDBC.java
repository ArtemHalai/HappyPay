package dao.jdbc;

import dao.intefaces.UserAccountDAO;
import dao.mappers.Mapper;
import dao.mappers.UserAccountMapper;
import model.UserAccount;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserAccountJDBC implements UserAccountDAO {
    private static final Logger LOG = Logger.getLogger(UserAccountJDBC.class);
    private Connection connection;

    public UserAccountJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(UserAccount userAccount) {

        String addUserAccount = "INSERT INTO user_account (user_id, currency, validity, balance, deposit, credit) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addUserAccount, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userAccount.getUserId());
            statement.setString(2, userAccount.getCurrency());
            statement.setDate(3, userAccount.getValidity());
            statement.setDouble(4, userAccount.getBalance());
            statement.setBoolean(5, userAccount.getDeposit());
            statement.setBoolean(6, userAccount.getCredit());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at add() method");
        }
        return false;
    }

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

    @Override
    public List<UserAccount> findAll() {
        List<UserAccount> list = new ArrayList<>();

        String findAll = "SELECT * FROM user_account";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            ResultSet rs = statement.executeQuery();

            Mapper<UserAccount> userAccountMapper = new UserAccountMapper();

            while (rs.next()) {
                UserAccount userAccount = userAccountMapper.getEntity(rs);
                list.add(userAccount);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at findAll() method");
        }
        return list;
    }

    @Override
    public boolean updateDepositStatusById(int id) {

        String updateStatus = "UPDATE user_account SET deposit = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateStatus, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBoolean(1, true);
            statement.setInt(2, id);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at updateDepositStatusById() method");
        }
        return false;
    }

    @Override
    public boolean updateCreditStatusById(int id) {

        String updateStatus = "UPDATE user_account SET credit = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateStatus, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBoolean(1, true);
            statement.setInt(2, id);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at updateCreditStatusById() method", e);
        }
        return false;
    }

    @Override
    public boolean updateBalanceById(int id, double amount) {

        String updateBalance = "UPDATE user_account SET balance = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateBalance, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, amount);
            statement.setInt(2, id);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at updateBalanceById() method");
        }
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at close() method");
        }
    }
}
