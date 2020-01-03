package dao.jdbc;

import dao.intefaces.DepositAccountDAO;
import dao.mappers.DepositAccountMapper;
import dao.mappers.Mapper;
import lombok.extern.log4j.Log4j;
import model.DepositAccount;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static enums.Attributes.TOTAL;

@Log4j
public class DepositAccountJDBC implements DepositAccountDAO {

    private Connection connection;

    public DepositAccountJDBC(Connection connection) {
        this.connection = connection;
    }

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
            log.error("SQLException occurred in DepositAccountJDBC.class at add() method");
        }
        return false;
    }

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
            log.error("SQLException occurred in DepositAccountJDBC.class at updateBalanceById() method");
        }
        return false;
    }

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
                log.error("SQLException occurred in DepositAccountJDBC.class at deleteDepositAccount() method");
            }
            return false;
        }
        return true;
    }

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
            log.error("SQLException occurred in DepositAccountJDBC.class at count() method");
        }
        return total;
    }

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
            log.error("SQLException occurred in DepositAccountJDBC.class at getAll() method");
        }
        return list;
    }

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
            log.error("SQLException occurred in DepositAccountJDBC.class at getById() method");
        }
        return depositAccount;
    }
}
