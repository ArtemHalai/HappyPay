package dao.jdbc;

import dao.intefaces.RefillDAO;
import dao.mappers.Mapper;
import dao.mappers.OperationMapper;
import dao.mappers.RefillMapper;
import model.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static enums.Attributes.TOTAL;
import static enums.OperationType.REFILL_DEPOSIT;

/**
 * Define a data access object used for executing refill requests to database using JDBC.
 * This class is implementation of RefillDAO.
 *
 * @see RefillDAO
 */
public class RefillJDBC implements RefillDAO {

    private static final Logger LOG = Logger.getLogger(RefillJDBC.class);
    private Connection connection;

    /**
     * Creates a RefillJDBC object with the connection {@link #connection}.
     *
     * @param connection The connection object.
     */
    public RefillJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method to get refill operations.
     *
     * @param paginationDTO The RefillPaginationDTO object.
     * @return The RefillPaginationDTO object containing data needed to get all refill operations part by part.
     * @see RefillPaginationDTO
     */
    @Override
    public RefillPaginationDTO getRefillOperations(RefillPaginationDTO paginationDTO) {
        String getRefillOperations = "SELECT * FROM refill_operation WHERE user_id = ? AND operation_type = ? LIMIT ? OFFSET ?";
        List<RefillOperation> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getRefillOperations)) {
            statement.setInt(1, paginationDTO.getUserId());
            statement.setString(2, REFILL_DEPOSIT.getName());
            statement.setInt(3, paginationDTO.getPageSize());
            statement.setInt(4, paginationDTO.getPageSize() * (paginationDTO.getPage() - 1));
            ResultSet rs = statement.executeQuery();
            Mapper<RefillOperation> refillMapper = new RefillMapper();
            while (rs.next())
                list.add(refillMapper.getEntity(rs));
        } catch (SQLException e) {
            LOG.error("SQLException occurred in RefillJDBC.class at getRefillOperations() method");
        }
        paginationDTO.setList(list);
        paginationDTO.setCount(count(paginationDTO.getUserId()));
        return paginationDTO;
    }

    /**
     * Method to get count of all refill operations in database by user id.
     *
     * @param userId The user id.
     * @return The int value representing amount of all refill operations in database by given user id.
     */
    @Override
    public int count(int userId) {
        String count = "SELECT COUNT(*) AS total FROM refill_operation WHERE user_id = ? AND operation_type = ?";
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(count)) {
            statement.setInt(1, userId);
            statement.setString(2, REFILL_DEPOSIT.getName());
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                total = rs.getInt(TOTAL.getName());
        } catch (SQLException e) {
            LOG.error("SQLException occurred in RefillJDBC.class at count() method");
        }
        return total;
    }

    /**
     * Method to add refill operation.
     *
     * @param refillOperation The RefillOperation object.
     * @return <code>true</code> if refill operation was added; <code>false</code> otherwise.
     * @see RefillOperation
     */
    @Override
    public boolean add(RefillOperation refillOperation) {

        String addOperation = "INSERT INTO refill_operation (user_id, amount, sender_account_type, operation_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addOperation)) {
            statement.setInt(1, refillOperation.getUserId());
            statement.setDouble(2, refillOperation.getAmount());
            statement.setString(3, refillOperation.getSenderAccountType());
            statement.setString(4, refillOperation.getOperationType());
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in RefillJDBC.class at add() method");
        }
        return false;
    }

    /**
     * Method to get limited list of refill operations.
     *
     * @param allOperationsDTO The AllOperationsDTO object.
     * @return The AllOperationsDTO object containing data needed to collect different types of operations.
     * @see AllOperationsDTO
     */
    @Override
    public AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO) {
        String getOperations = "SELECT * FROM refill_operation WHERE user_id = ? ORDER BY date DESC LIMIT ?";
        List<OperationsData> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getOperations)) {
            statement.setInt(1, allOperationsDTO.getUserId());
            statement.setInt(2, allOperationsDTO.getPageSize());
            ResultSet rs = statement.executeQuery();
            Mapper<OperationsData> operationMapper = new OperationMapper();
            while (rs.next())
                list.add(operationMapper.getEntity(rs));
        } catch (SQLException e) {
            LOG.error("SQLException occurred in RefillJDBC.class at getLimitOperations() method");
        }
        AllOperationsDTO operationsDTO = new AllOperationsDTO();
        operationsDTO.setUserId(allOperationsDTO.getUserId());
        operationsDTO.setList(list);
        return operationsDTO;
    }

    /**
     * Method to get refill operation by id.
     *
     * @param id The user id.
     * @return The RefillOperation object.
     * @see RefillOperation
     */
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
}
