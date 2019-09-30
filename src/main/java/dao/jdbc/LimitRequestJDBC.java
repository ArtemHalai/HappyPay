package dao.jdbc;

import dao.intefaces.LimitRequestDAO;
import dao.mappers.LimitRequestAdminMapper;
import dao.mappers.LimitRequestMapper;
import dao.mappers.Mapper;
import model.LimitRequest;
import model.LimitRequestAdmin;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Define a data access object used for executing limit requests to database using JDBC.
 * This class is implementation of LimitRequestDAO.
 *
 * @see LimitRequestDAO
 */
public class LimitRequestJDBC implements LimitRequestDAO {

    private static final Logger LOG = Logger.getLogger(CreditApprovementJDBC.class);
    private Connection connection;

    /**
     * Creates a LimitRequestJDBC object with the connection {@link #connection}.
     *
     * @param connection The connection object.
     */
    public LimitRequestJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method to add limit request.
     *
     * @param limitRequest The LimitRequest object.
     * @return <code>true</code> if limit request was added; <code>false</code> otherwise.
     * @see LimitRequest
     */
    @Override
    public boolean add(LimitRequest limitRequest) {
        String add = "INSERT INTO limit_request (user_id, amount, decision, operation_date) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add)) {
            statement.setInt(1, limitRequest.getUserId());
            statement.setDouble(2, limitRequest.getAmount());
            statement.setBoolean(3, limitRequest.getDecision());
            statement.setDate(4, limitRequest.getOperationDate(), Calendar.getInstance());
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in LimitRequestJDBC.class at add() method");
        }
        return false;
    }

    /**
     * Method to get all limit requests by decision.
     *
     * @param decision The boolean value representing decision of limit request.
     * @return The list containing all limit requests with given decision.
     * @see LimitRequestAdmin
     */
    @Override
    public List<LimitRequestAdmin> findAllByDecision(boolean decision) {
        List<LimitRequestAdmin> list = new ArrayList<>();

        String findAll = "SELECT * FROM limit_request LEFT JOIN user_account" +
                " ON limit_request.user_id = user_account.user_id LEFT JOIN credit_accounts " +
                "ON limit_request.user_id = credit_accounts.user_id WHERE decision = ?";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            statement.setBoolean(1, decision);
            ResultSet rs = statement.executeQuery();

            Mapper<LimitRequestAdmin> limitRequestAdminMapper = new LimitRequestAdminMapper();

            while (rs.next()) {
                LimitRequestAdmin limitRequest = limitRequestAdminMapper.getEntity(rs);
                list.add(limitRequest);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in LimitRequestJDBC.class at findAllByDecision() method");
        }
        return list;
    }

    /**
     * Method to update decision of limit request by user id.
     *
     * @param decision The boolean value representing decision of limit request.
     * @param userId The user id.
     * @return <code>true</code> if decision was updated; <code>false</code> otherwise.
     */
    @Override
    public boolean updateDecision(boolean decision, int userId) {
        String updateDecision = "UPDATE limit_request SET decision = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateDecision)) {
            statement.setBoolean(1, decision);
            statement.setInt(2, userId);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in LimitRequestJDBC.class at updateDecision() method");
        }
        return false;
    }

    /**
     * Method to delete limit request by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if request was deleted; <code>false</code> otherwise.
     */
    @Override
    public boolean deleteRequest(int userId) {
        String deleteRequest = "DELETE FROM limit_request WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
            statement.setInt(1, userId);
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in LimitRequestJDBC.class at deleteRequest() method");
        }
        return false;
    }

    /**
     * Method to get limit request by id.
     *
     * @param id The user id.
     * @return The LimitRequest object.
     * @see LimitRequest
     */
    @Override
    public LimitRequest getById(int id) {
        Mapper<LimitRequest> limitRequestMapper = new LimitRequestMapper();
        LimitRequest limitRequest = new LimitRequest();
        limitRequest.setUserId(-1);

        String getById = "SELECT * FROM limit_request WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                limitRequest = limitRequestMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in LimitRequestJDBC.class at getById() method");
        }
        return limitRequest;
    }
}
