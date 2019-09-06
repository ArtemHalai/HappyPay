package dao.jdbc;

import dao.intefaces.DepositAccountDAO;
import dao.mappers.DepositAccountMapper;
import dao.mappers.Mapper;
import model.DepositAccount;
import model.RefillOperation;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepositAccountJDBC implements DepositAccountDAO {

    private static final Logger LOG = Logger.getLogger(DepositAccountJDBC.class);
    private Connection connection;

    public DepositAccountJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(DepositAccount depositAccount) {

        String add = "INSERT INTO deposit_accounts (user_id, balance, currency,  rate, term) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, depositAccount.getUserId());
            statement.setDouble(2, depositAccount.getBalance());
            statement.setString(3, depositAccount.getCurrency());
            statement.setDouble(4, depositAccount.getRate());
            statement.setDate(5, depositAccount.getTerm());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at add() method");
        }
        return false;
    }

    @Override
    public boolean updateBalanceById(double amount, int userId) {
        String updateBalance = "UPDATE deposit_accounts SET balance = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateBalance, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at updateBalanceById() method");
        }
        return false;
    }

    @Override
    public boolean updateBalanceByAccount(double amount, long account) {
        String updateBalance = "UPDATE deposit_accounts SET balance = ? WHERE account_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateBalance, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, amount);
            statement.setLong(2, account);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at updateBalanceByAccount() method");
        }
        return false;
    }

    @Override
    public DepositAccount isAccountNumberExist(long accountNumber) {
        Mapper<DepositAccount> depositAccountMapper = new DepositAccountMapper();
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setUserId(-1);
        String getById = "SELECT * FROM deposit_accounts WHERE account_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setLong(1, accountNumber);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                depositAccount = depositAccountMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at getByAccountNumber() method");
        }
        return depositAccount;
    }

    @Override
    public DepositAccount getByAccountAndIban(RefillOperation refillOperation) {
        Mapper<DepositAccount> depositAccountMapper = new DepositAccountMapper();
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setUserId(-1);
        String getById = "SELECT * FROM deposit_accounts WHERE account_number = ? AND iban = ?";
        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setLong(1, refillOperation.getAccountNumber());
            statement.setLong(2, refillOperation.getSenderIBAN());
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                depositAccount = depositAccountMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at getByAccountAndIban() method");
        }
        return depositAccount;
    }

    @Override
    public DepositAccount getById(int id) {
        Mapper<DepositAccount> depositAccountMapper = new DepositAccountMapper();
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setUserId(-1);

        String getById = "SELECT * FROM deposit_accounts LEFT JOIN refill_operation ON deposit_accounts.user_id = " +
                " refill_operation.user_id WHERE user_id = ?";
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

    @Override
    public List<DepositAccount> findAll() {
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
            LOG.error("SQLException occurred in DepositAccountJDBC.class at findAll() method");
        }
        return list;
    }
}
