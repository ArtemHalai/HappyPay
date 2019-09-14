package dao.jdbc;

import dao.intefaces.LimitRequestDAO;
import dao.mappers.LimiRequestMapper;
import dao.mappers.LimitRequestAdminMapper;
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

public class LimitRequestJDBC implements LimitRequestDAO {

    private static final Logger LOG = Logger.getLogger(CreditApprovementJDBC.class);
    private Connection connection;

    public LimitRequestJDBC(Connection connection) {
        this.connection = connection;
    }

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

    @Override
    public LimitRequest getById(int id) {
        Mapper<LimitRequest> limitRequestMapper = new LimiRequestMapper();
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
