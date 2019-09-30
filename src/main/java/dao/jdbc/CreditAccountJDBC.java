package dao.jdbc;

import dao.intefaces.CreditAccountDAO;
import dao.mappers.CreditAccountMapper;
import dao.mappers.Mapper;
import model.CreditAccount;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Define a data access object used for executing credit account's requests to database using JDBC.
 * This class is implementation of CreditAccountDAO.
 *
 * @see CreditAccountDAO
 */
public class CreditAccountJDBC implements CreditAccountDAO {

    private static final Logger LOG = Logger.getLogger(CreditAccountJDBC.class);
    private Connection connection;

    /**
     * Creates a CreditAccountJDBC object with the connection {@link #connection}.
     *
     * @param connection The connection object.
     */
    public CreditAccountJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method to add credit account.
     *
     * @param creditAccount The CreditAccount object.
     * @return <code>true</code> if credit account was added; <code>false</code> otherwise.
     * @see CreditAccount
     */
    @Override
    public boolean add(CreditAccount creditAccount) {

        String add = "INSERT INTO credit_accounts (user_id, currency, credit_limit, rate, " +
                "arrears, interest_charges, account_number) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add)) {
            statement.setInt(1, creditAccount.getUserId());
            statement.setString(2, creditAccount.getCurrency());
            statement.setDouble(3, creditAccount.getLimit());
            statement.setDouble(4, creditAccount.getRate());
            statement.setDouble(5, creditAccount.getArrears());
            statement.setDouble(6, creditAccount.getInterestCharges());
            statement.setLong(7, creditAccount.getAccountNumber());
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditAccountJDBC.class at add() method");
        }
        return false;
    }

    /**
     * Method to update balance of credit account by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if balance was updated; <code>false</code> otherwise.
     */
    @Override
    public boolean updateBalanceById(double amount, int userId) {
        String updateBalance = "UPDATE credit_accounts SET credit_limit = ? WHERE user_id = ?";
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
     * Method to pay arrears by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if arrears have been payed; <code>false</code> otherwise.
     */
    @Override
    public boolean payArrears(double amount, int userId) {
        String payArrears = "UPDATE credit_accounts SET arrears = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(payArrears)) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditAccountJDBC.class at payArrears() method");
        }
        return false;
    }

    /**
     * Method to get all credit accounts.
     *
     * @return The list containing all credit accounts.
     * @see CreditAccount
     */
    @Override
    public List<CreditAccount> getAll() {
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
            LOG.error("SQLException occurred in CreditAccountJDBC.class at getAll() method");
        }
        return list;
    }

    /**
     * Method to update interest charges of credit account by user id.
     *
     * @param amount The amount.
     * @param userId The user id.
     * @return <code>true</code> if interest charges were updated; <code>false</code> otherwise.
     */
    @Override
    public boolean updateInterestCharges(double amount, int userId) {
        String payArrears = "UPDATE credit_accounts SET interest_charges = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(payArrears)) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditAccountJDBC.class at updateInterestCharges() method");
        }
        return false;
    }

    /**
     * Method to get credit account by id.
     *
     * @param id The user id.
     * @return The CreditAccount object.
     * @see CreditAccount
     */
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
}
