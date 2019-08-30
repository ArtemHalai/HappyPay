package dao.jdbc;

import dao.intefaces.RefillDAO;
import dao.mappers.Mapper;
import dao.mappers.RefillMapper;
import model.RefillOperation;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RefillJDBC implements RefillDAO {

    private static final Logger LOG = Logger.getLogger(RefillJDBC.class);
    private Connection connection;

    public RefillJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<RefillOperation> getAllById(int id) {

        List<RefillOperation> list = new ArrayList<>();

        String findAll = "SELECT * FROM refill_operation WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            Mapper<RefillOperation> refillMapper = new RefillMapper();
            while (rs.next()) {
                RefillOperation refillOperation = refillMapper.getEntity(rs);
                list.add(refillOperation);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in RefillJDBC.class at getAllById() method");
        }
        return list;
    }

    @Override
    public boolean add(RefillOperation refillOperation) {

        String addOperation = "INSERT INTO refill_operation (user_id, amount, account_number) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addOperation, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, refillOperation.getUserId());
            statement.setDouble(2, refillOperation.getAmount());
            statement.setString(3, refillOperation.getAccountNumber());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in RefillJDBC.class at add() method");
        }
        return false;
    }

    @Override
    public RefillOperation getById(int id) {
        Mapper<RefillOperation> refillOperationMapper = new RefillMapper();
        RefillOperation refillOperation = new RefillOperation();
        refillOperation.setUserId(-1);

        String getById = "SELECT * FROM refill_operation WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                refillOperation = refillOperationMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in RefillJDBC.class at getById() method");
        }
        return refillOperation;
    }

    @Override
    public List<RefillOperation> findAll() {
        List<RefillOperation> list = new ArrayList<>();

        String findAll = "SELECT * FROM refill_operation";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            ResultSet rs = statement.executeQuery();

            Mapper<RefillOperation> refillOperationMapper = new RefillMapper();

            while (rs.next()) {
                RefillOperation refillOperation = refillOperationMapper.getEntity(rs);
                list.add(refillOperation);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in RefillJDBC.class at findAll() method");
        }
        return list;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in RefillJDBC.class at close() method");
        }
    }
}
