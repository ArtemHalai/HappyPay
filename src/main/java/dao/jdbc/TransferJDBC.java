package dao.jdbc;

import dao.intefaces.TransferDAO;
import dao.mappers.Mapper;
import dao.mappers.OperationMapper;
import dao.mappers.TransferMapper;
import model.AllOperationsDTO;
import model.OperationsData;
import model.TransferOperation;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static enums.Attributes.TOTAL;

/**
 * Define a data access object used for executing transfer requests to database using JDBC.
 * This class is implementation of TransferDAO.
 *
 * @see TransferDAO
 */
public class TransferJDBC implements TransferDAO {

    private static final Logger LOG = Logger.getLogger(TransferJDBC.class);
    private Connection connection;

    /**
     * Creates a TransferJDBC object with the connection {@link #connection}.
     *
     * @param connection The connection object.
     */
    public TransferJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method to add transfer operation.
     *
     * @param transferOperation The TransferOperation object.
     * @return <code>true</code> if transfer operation was added; <code>false</code> otherwise.
     * @see TransferOperation
     */
    @Override
    public boolean add(TransferOperation transferOperation) {

        String addOperation = "INSERT INTO transfer_operation (user_id, recipient_account_number, amount, operation_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addOperation)) {
            statement.setInt(1, transferOperation.getUserId());
            statement.setLong(2, transferOperation.getRecipientAccountNumber());
            statement.setDouble(3, transferOperation.getAmount());
            statement.setString(4, transferOperation.getOperationType());
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransferJDBC.class at create() method");
        }
        return false;
    }

    /**
     * Method to get count of all transfer operations in database by user id.
     *
     * @param userId The user id.
     * @return The int value representing amount of all transfer operations in database by given user id.
     */
    @Override
    public int count(int userId) {
        String count = "SELECT COUNT(*) AS total FROM transfer_operation WHERE user_id = ?";
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(count)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                total = rs.getInt(TOTAL.getName());
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransferJDBC.class at count() method");
        }
        return total;
    }

    /**
     * Method to get limited list of transfer operations.
     *
     * @param allOperationsDTO The AllOperationsDTO object.
     * @return The AllOperationsDTO object containing data needed to collect different types of operations.
     * @see AllOperationsDTO
     */
    @Override
    public AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO) {
        String getOperations = "SELECT * FROM transfer_operation WHERE user_id = ? ORDER BY date DESC LIMIT ?";
        List<OperationsData> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getOperations)) {
            statement.setInt(1, allOperationsDTO.getUserId());
            statement.setInt(2, allOperationsDTO.getPageSize());
            ResultSet rs = statement.executeQuery();
            Mapper<OperationsData> operationMapper = new OperationMapper();
            while (rs.next())
                list.add(operationMapper.getEntity(rs));
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransferJDBC.class at getLimitOperations() method");
        }
        AllOperationsDTO operationsDTO = new AllOperationsDTO();
        operationsDTO.setUserId(allOperationsDTO.getUserId());
        operationsDTO.setList(list);
        return operationsDTO;
    }

    /**
     * Method to get transfer operation by id.
     *
     * @param id The user id.
     * @return The TransferOperation object.
     * @see TransferOperation
     */
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
}
