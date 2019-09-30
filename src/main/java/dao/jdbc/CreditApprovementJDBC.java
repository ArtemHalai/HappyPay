package dao.jdbc;

import dao.intefaces.CreditApprovementDAO;
import dao.mappers.CreditApprovementMapper;
import dao.mappers.CreditRequestAdminMapper;
import dao.mappers.Mapper;
import dao.mappers.OperationMapper;
import model.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static enums.Attributes.TOTAL;

/**
 * Define a data access object used for executing credit approvement's requests to database using JDBC.
 * This class is implementation of CreditApprovementDAO.
 *
 * @see CreditApprovementDAO
 */
public class CreditApprovementJDBC implements CreditApprovementDAO {

    private static final Logger LOG = Logger.getLogger(CreditApprovementJDBC.class);
    private Connection connection;

    /**
     * Creates a CreditApprovementJDBC object with the connection {@link #connection}.
     *
     * @param connection The connection object.
     */
    public CreditApprovementJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method to create credit request.
     *
     * @param request The CreditRequest object.
     * @return <code>true</code> if credit request was created; <code>false</code> otherwise.
     * @see CreditRequest
     */
    @Override
    public boolean createCreditRequest(CreditRequest request) {

        String add = "INSERT INTO credit_approvement_operation (user_id, amount, decision)" +
                " VALUES(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add)) {
            statement.setInt(1, request.getUserId());
            statement.setDouble(2, request.getAmount());
            statement.setBoolean(3, request.getDecision());
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at createCreditRequest() method");
        }
        return false;
    }

    /**
     * Method to get count of all credit approvements in database by user id.
     *
     * @param userId The user id.
     * @return The int value representing amount of all credit approvements in database.
     */
    @Override
    public int count(int userId) {
        String count = "SELECT COUNT(*) AS total FROM credit_approvement_operation WHERE user_id = ?";
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(count)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                total = rs.getInt(TOTAL.getName());
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at count() method");
        }
        return total;
    }

    /**
     * Method to get credit approvement operation by id.
     *
     * @param id The user id.
     * @return The CreditApprovementOperation object.
     * @see CreditApprovementOperation
     */
    @Override
    public CreditApprovementOperation getById(int id) {
        Mapper<CreditApprovementOperation> creditApprovementOperationMapper = new CreditApprovementMapper();
        CreditApprovementOperation creditApprovementOperation = new CreditApprovementOperation();
        creditApprovementOperation.setUserId(-1);

        String getById = "SELECT * FROM credit_approvement_operation WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                creditApprovementOperation = creditApprovementOperationMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at getById() method");
        }
        return creditApprovementOperation;
    }

    /**
     * Method to get all credit requests by decision.
     *
     * @param decision The boolean value representing decision of credit request.
     * @return The list containing all credit requests with given decision.
     * @see CreditRequestAdmin
     */
    @Override
    public List<CreditRequestAdmin> findAllByDecision(boolean decision) {
        List<CreditRequestAdmin> list = new ArrayList<>();

        String findAll = "SELECT * FROM credit_approvement_operation " +
                "LEFT JOIN user_account ON credit_approvement_operation.user_id = user_account.user_id WHERE decision = ?";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            statement.setBoolean(1, decision);
            ResultSet rs = statement.executeQuery();

            Mapper<CreditRequestAdmin> creditRequestAdminMapper = new CreditRequestAdminMapper();

            while (rs.next()) {
                CreditRequestAdmin creditRequestAdmin = creditRequestAdminMapper.getEntity(rs);
                list.add(creditRequestAdmin);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at findAllByDecision() method");
        }
        return list;
    }

    /**
     * Method to update decision of credit request by user id.
     *
     * @param decision The boolean value representing decision of credit request.
     * @param userId The user id.
     * @return <code>true</code> if decision was updated; <code>false</code> otherwise.
     */
    @Override
    public boolean updateDecision(boolean decision, int userId) {
        String updateDecision = "UPDATE credit_approvement_operation SET decision = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateDecision)) {
            statement.setBoolean(1, decision);
            statement.setInt(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at updateDecision() method");
        }
        return false;
    }

    /**
     * Method to delete credit request by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if request was deleted; <code>false</code> otherwise.
     */
    @Override
    public boolean deleteRequest(int userId) {
        String deleteRequest = "DELETE FROM credit_approvement_operation WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
            statement.setInt(1, userId);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at deleteRequest() method");
        }
        return false;
    }
}
