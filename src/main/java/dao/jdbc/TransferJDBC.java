package dao.jdbc;

import dao.intefaces.TransferDAO;
import dao.mappers.Mapper;
import dao.mappers.OperationMapper;
import dao.mappers.TransferMapper;
import lombok.extern.log4j.Log4j;
import model.AllOperationsDTO;
import model.OperationsData;
import model.TransferOperation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static enums.Attributes.TOTAL;

@Log4j
public class TransferJDBC implements TransferDAO {

    private Connection connection;

    public TransferJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(TransferOperation transferOperation) {

        String addOperation = "INSERT INTO transfer_operation (user_id, recipient_account_number, amount, operation_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addOperation)) {
            statement.setInt(1, transferOperation.getUserId());
            statement.setLong(2, transferOperation.getRecipientAccountNumber());
            statement.setDouble(3, transferOperation.getAmount());
            statement.setString(4, transferOperation.getOperationType());
            int generated = statement.executeUpdate();
            if (generated > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Could not add TransferOperation", e);
        }
        return false;
    }

    @Override
    public int count(int userId) {
        String count = "SELECT COUNT(*) AS total FROM transfer_operation WHERE user_id = ?";
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(count)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                total = rs.getInt(TOTAL.getName());
            }
        } catch (SQLException e) {
            log.error("Could not count by id", e);
        }
        return total;
    }

    @Override
    public AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO) {
        String getOperations = "SELECT * FROM transfer_operation WHERE user_id = ? ORDER BY date DESC LIMIT ?";
        List<OperationsData> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getOperations)) {
            statement.setInt(1, allOperationsDTO.getUserId());
            statement.setInt(2, allOperationsDTO.getPageSize());
            ResultSet rs = statement.executeQuery();
            Mapper<OperationsData> operationMapper = new OperationMapper();
            while (rs.next()) {
                list.add(operationMapper.getEntity(rs));
            }
        } catch (SQLException e) {
            log.error("Could not get limit operations", e);
        }
        AllOperationsDTO operationsDTO = new AllOperationsDTO();
        operationsDTO.setUserId(allOperationsDTO.getUserId());
        operationsDTO.setList(list);
        return operationsDTO;
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
            if (rs.next()) {
                transferOperation = transferMapper.getEntity(rs);
            }
        } catch (SQLException e) {
            log.error("Could not get TransferOperation by id", e);
        }
        return transferOperation;
    }
}
