package dao.jdbc;

import dao.intefaces.CreditApprovementDAO;
import dao.mappers.CreditApprovementMapper;
import dao.mappers.Mapper;
import model.CreditApprovementOperation;
import model.CreditRequest;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditApprovementJDBC implements CreditApprovementDAO {

    private static final Logger LOG = Logger.getLogger(CreditApprovementJDBC.class);
    private Connection connection;

    public CreditApprovementJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(CreditApprovementOperation creditApprovementOperation) {

        String add = "INSERT INTO credit_approvement_operation (user_id, manager_id, amount, decision, operation_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, creditApprovementOperation.getUserId());
            statement.setInt(2, creditApprovementOperation.getManagerId());
            statement.setDouble(3, creditApprovementOperation.getAmount());
            statement.setBoolean(4, creditApprovementOperation.getDecision());
            statement.setDate(5, creditApprovementOperation.getDate());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at add() method");
        }
        return false;
    }

    @Override
    public boolean createCreditRequest(CreditRequest request) {

        String add = "INSERT INTO credit_approvement_operation (user_id, amount, decision) VALUES(?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, request.getUserId());
            statement.setDouble(2, request.getAmount());
            statement.setBoolean(3, request.getDecision());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at createCreditRequest() method");
        }
        return false;
    }

    @Override
    public List<CreditApprovementOperation> getAllById(int id) {
        List<CreditApprovementOperation> list = new ArrayList<>();

        String findAll = "SELECT * FROM credit_approvement_operation WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            Mapper<CreditApprovementOperation> creditApprovementMapper = new CreditApprovementMapper();
            while (rs.next()) {
                CreditApprovementOperation creditApprovementOperation = creditApprovementMapper.getEntity(rs);
                list.add(creditApprovementOperation);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at getAllById() method");
        }
        return list;
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
    public List<CreditApprovementOperation> findAll() {
        List<CreditApprovementOperation> list = new ArrayList<>();

        String findAll = "SELECT * FROM credit_approvement_operation";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            ResultSet rs = statement.executeQuery();

            Mapper<CreditApprovementOperation> creditApprovementOperationMapper = new CreditApprovementMapper();

            while (rs.next()) {
                CreditApprovementOperation creditApprovementOperation = creditApprovementOperationMapper.getEntity(rs);
                list.add(creditApprovementOperation);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at findAll() method");
        }
        return list;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in CreditApprovementJDBC.class at close() method");
        }
    }
}
