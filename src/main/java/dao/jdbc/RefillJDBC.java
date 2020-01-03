package dao.jdbc;

import dao.intefaces.RefillDAO;
import dao.mappers.Mapper;
import dao.mappers.OperationMapper;
import dao.mappers.RefillMapper;
import lombok.extern.log4j.Log4j;
import model.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static enums.Attributes.TOTAL;
import static enums.OperationType.REFILL_DEPOSIT;

@Log4j
public class RefillJDBC implements RefillDAO {

    private Connection connection;

    public RefillJDBC(Connection connection) {
        this.connection = connection;
    }

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
            log.error("SQLException occurred in RefillJDBC.class at getRefillOperations() method");
        }
        paginationDTO.setList(list);
        paginationDTO.setCount(count(paginationDTO.getUserId()));
        return paginationDTO;
    }

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
            log.error("SQLException occurred in RefillJDBC.class at count() method");
        }
        return total;
    }

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
            log.error("SQLException occurred in RefillJDBC.class at add() method");
        }
        return false;
    }

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
            log.error("SQLException occurred in RefillJDBC.class at getLimitOperations() method");
        }
        AllOperationsDTO operationsDTO = new AllOperationsDTO();
        operationsDTO.setUserId(allOperationsDTO.getUserId());
        operationsDTO.setList(list);
        return operationsDTO;
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
            log.error("SQLException occurred in RefillJDBC.class at getById() method");
        }
        return refillOperation;
    }
}
