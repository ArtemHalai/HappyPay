package dao.jdbc;

import dao.intefaces.DepositAccountDAO;
import dao.mappers.DepositAccountMapper;
import dao.mappers.Mapper;
import model.DepositAccount;
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

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in DepositAccountJDBC.class at close() method");
        }
    }
}
