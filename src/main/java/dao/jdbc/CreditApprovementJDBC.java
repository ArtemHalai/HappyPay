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

public class CreditApprovementJDBC implements CreditApprovementDAO {

    private static final Logger LOG = Logger.getLogger(CreditApprovementJDBC.class);
    private Connection connection;

    public CreditApprovementJDBC(Connection connection) {
        this.connection = connection;
    }

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
