package dao.jdbc;

import dao.intefaces.CreditApprovementDAO;
import dao.mappers.CreditApprovementMapper;
import dao.mappers.CreditRequestAdminMapper;
import dao.mappers.Mapper;
import lombok.extern.log4j.Log4j;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static enums.Attributes.TOTAL;

@Log4j
public class CreditApprovementJDBC implements CreditApprovementDAO {

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
            statement.setBoolean(3, request.isDecision());
            int generated = statement.executeUpdate();
            if (generated > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Could not create CreditRequest", e);
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
            if (rs.next()) {
                total = rs.getInt(TOTAL.getName());
            }
        } catch (SQLException e) {
            log.error("Could not count by userId", e);
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
            if (rs.next()) {
                creditApprovementOperation = creditApprovementOperationMapper.getEntity(rs);
            }
        } catch (SQLException e) {
            log.error("Could not get CreditApprovementOperation by id", e);
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
            log.error("Could not find all CreditRequestAdmin objects by decision", e);
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
            if (generated > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Could not update decision", e);
        }
        return false;
    }

    @Override
    public boolean deleteRequest(int userId) {
        String deleteRequest = "DELETE FROM credit_approvement_operation WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
            statement.setInt(1, userId);
            int generated = statement.executeUpdate();
            if (generated > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Could not delete request", e);
        }
        return false;
    }
}
