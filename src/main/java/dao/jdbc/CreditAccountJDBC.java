package dao.jdbc;

import dao.intefaces.CreditAccountDAO;
import dao.mappers.CreditAccountMapper;
import dao.mappers.Mapper;
import model.CreditAccount;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditAccountJDBC implements CreditAccountDAO {

    private static final Logger LOG = Logger.getLogger(CreditAccountJDBC.class);
    private Connection connection;

    public CreditAccountJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(CreditAccount creditAccount) {

        String add = "INSERT INTO credit_accounts (user_id, balance, currency, credit_limit, rate, " +
                "arrears, interest_charges) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, creditAccount.getUserId());
            statement.setDouble(2, creditAccount.getBalance());
            statement.setString(3, creditAccount.getCurrency());
            statement.setDouble(4, creditAccount.getLimit());
            statement.setDouble(5, creditAccount.getRate());
            statement.setDouble(6, creditAccount.getArrear());
            statement.setDouble(7, creditAccount.getInterestCharges());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditAccountJDBC.class at add() method");
        }
        return false;
    }

    @Override
    public CreditAccount getById(int id) {
        Mapper<CreditAccount> creditAccountMapper = new CreditAccountMapper();
        CreditAccount creditAccount = new CreditAccount();
        creditAccount.setUserId(-1);

        String getById = "SELECT * FROM credit_accounts WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                creditAccount = creditAccountMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditAccountJDBC.class at getById() method");
        }
        return creditAccount;
    }

    @Override
    public List<CreditAccount> findAll() {
        List<CreditAccount> list = new ArrayList<>();

        String findAll = "SELECT * FROM credit_accounts";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            ResultSet rs = statement.executeQuery();

            Mapper<CreditAccount> creditAccountMapper = new CreditAccountMapper();

            while (rs.next()) {
                CreditAccount creditAccount = creditAccountMapper.getEntity(rs);
                list.add(creditAccount);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditAccountJDBC.class at findAll() method");
        }
        return list;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditAccountJDBC.class at close() method");
        }
    }
}
