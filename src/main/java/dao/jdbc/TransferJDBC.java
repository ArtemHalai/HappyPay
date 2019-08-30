package dao.jdbc;

import dao.intefaces.TransferDAO;
import dao.mappers.Mapper;
import dao.mappers.TransferMapper;
import model.TransferOperation;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferJDBC implements TransferDAO {

    private static final Logger LOG = Logger.getLogger(TransferJDBC.class);
    private Connection connection;

    public TransferJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<TransferOperation> getAllById(int id) {

        List<TransferOperation> list = new ArrayList<>();

        String findAll = "SELECT * FROM transfer_operation WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            Mapper<TransferOperation> transferMapper = new TransferMapper();
            while (rs.next()) {
                TransferOperation transferOperation = transferMapper.getEntity(rs);
                list.add(transferOperation);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransferJDBC.class at getAllById() method");
        }
        return list;
    }

    @Override
    public boolean add(TransferOperation transferOperation) {

        String addOperation = "INSERT INTO transfer_operation (user_id, recipient_account_number, amount) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addOperation, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, transferOperation.getUserId());
            statement.setString(2, transferOperation.getRecipientAccountNumber());
            statement.setDouble(3, transferOperation.getAmount());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransferJDBC.class at create() method");
        }
        return false;
    }

    @Override
    public TransferOperation getById(int id) {
        Mapper<TransferOperation> transferMapper = new TransferMapper();
        TransferOperation transferOperation = new TransferOperation();
        transferOperation.setUserId(-1);

        String getById = "SELECT * FROM transfer_operation WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                transferOperation = transferMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at getById() method");
        }
        return transferOperation;
    }

    @Override
    public List<TransferOperation> findAll() {
        List<TransferOperation> list = new ArrayList<>();

        String findAll = "SELECT * FROM transfer_operation";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            ResultSet rs = statement.executeQuery();

            Mapper<TransferOperation> transferMapper = new TransferMapper();

            while (rs.next()) {
                TransferOperation transferOperation = transferMapper.getEntity(rs);
                list.add(transferOperation);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in UserAccountJDBC.class at findAll() method");
        }
        return list;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransferJDBC.class at close() method");
        }
    }
}
