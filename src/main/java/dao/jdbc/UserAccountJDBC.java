package dao.jdbc;

import dao.intefaces.UserAccountDAO;
import dao.mappers.Mapper;
import dao.mappers.UserAccountMapper;
import lombok.extern.log4j.Log4j;
import model.UserAccount;
import util.DateValidity;
import util.SqlDateLocalDateConverter;

import java.sql.*;
import java.util.Calendar;

@Log4j
public class UserAccountJDBC implements UserAccountDAO {

    private Connection connection;

    public UserAccountJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(UserAccount userAccount) {

        String addUserAccount = "INSERT INTO user_account (user_id, validity, deposit, credit) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addUserAccount)) {
            Date date = SqlDateLocalDateConverter.convertLocalDateToSqlDate(userAccount.getValidity());

            statement.setInt(1, userAccount.getUserId());
            statement.setDate(2, date, Calendar.getInstance());
            statement.setBoolean(3, userAccount.isDeposit());
            statement.setBoolean(4, userAccount.isCredit());
            int generated = statement.executeUpdate();
            if (generated > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Could not add UserAccount", e);
        }
        return false;
    }

    @Override
    public boolean updateBalanceById(double amount, int userId) {
        String updateBalance = "UPDATE user_account SET balance = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateBalance)) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Could not update balance by id", e);
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
            if (rs.next()) {
                userAccount = userAccountMapper.getEntity(rs);
            }
        } catch (SQLException e) {
            log.error("Could not get UserAccount by id", e);
        }
        return userAccount;
    }

    @Override
    public boolean updateCreditStatusById(int id, boolean decision) {
        String updateStatus = "UPDATE user_account SET credit = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateStatus)) {
            statement.setBoolean(1, decision);
            statement.setInt(2, id);
            int generated = statement.executeUpdate();
            if (generated > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Could not update credit status by id", e);
        }
        return false;
    }

    @Override
    public boolean updateTerm(int userId) {
        String updateTerm = "UPDATE user_account SET validity = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateTerm)) {
            Date date = SqlDateLocalDateConverter.convertLocalDateToSqlDate(DateValidity.getValidity());

            statement.setDate(1, date, Calendar.getInstance());
            statement.setLong(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Could not update term by id", e);
        }
        return false;
    }

    @Override
    public UserAccount getByAccountNumber(long recipientAccountNumber) {
        Mapper<UserAccount> userAccountMapper = new UserAccountMapper();
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(-1);

        String getById = "SELECT * FROM user_account WHERE account_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setLong(1, recipientAccountNumber);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                userAccount = userAccountMapper.getEntity(rs);
            }
        } catch (SQLException e) {
            log.error("Could not get UserAccount by account number", e);
        }
        return userAccount;
    }

    @Override
    public boolean updateByAccount(double amount, long recipientAccountNumber) {
        String updateBalance = "UPDATE user_account SET balance = ? WHERE account_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateBalance)) {
            statement.setDouble(1, amount);
            statement.setLong(2, recipientAccountNumber);
            int generated = statement.executeUpdate();
            if (generated > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Could not update balance by account number", e);
        }
        return false;
    }

    @Override
    public boolean updateDepositStatusById(int userId, boolean decision) {
        String updateStatus = "UPDATE user_account SET deposit = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateStatus)) {
            statement.setBoolean(1, decision);
            statement.setInt(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Could not update deposit status by id", e);
        }
        return false;
    }
}
