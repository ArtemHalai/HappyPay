package dao.jdbc;

import dao.intefaces.BillPaymentDAO;
import dao.mappers.BillPaymentMapper;
import dao.mappers.Mapper;
import dao.mappers.OperationMapper;
import model.AllOperationsDTO;
import model.BillPaymentOperation;
import model.OperationsData;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static enums.Attributes.TOTAL;

public class BillPaymentJDBC implements BillPaymentDAO {

    private static final Logger LOG = Logger.getLogger(BillPaymentJDBC.class);
    private Connection connection;

    public BillPaymentJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(BillPaymentOperation billPaymentOperation) {

        String add = "INSERT INTO bill_payment_operation (user_id, bill_number, purpose, amount, operation_type) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add)) {
            statement.setInt(1, billPaymentOperation.getUserId());
            statement.setLong(2, billPaymentOperation.getBillNumber());
            statement.setString(3, billPaymentOperation.getPurpose());
            statement.setDouble(4, billPaymentOperation.getAmount());
            statement.setString(5, billPaymentOperation.getOperationType());
            int generated = statement.executeUpdate();
            if (generated > 0)
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in BillPaymentJDBC.class at add() method");
        }
        return false;
    }

    @Override
    public int count(int userId) {
        String count = "SELECT COUNT(*) AS total FROM bill_payment_operation WHERE user_id = ?";
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(count)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                total = rs.getInt(TOTAL.getName());
        } catch (SQLException e) {
            LOG.error("SQLException occurred in BillPaymentJDBC.class at count() method");
        }
        return total;
    }

    @Override
    public AllOperationsDTO getLimitOperations(AllOperationsDTO allOperationsDTO) {
        String getOperations = "SELECT * FROM bill_payment_operation WHERE user_id = ? ORDER BY date DESC LIMIT ?";
        List<OperationsData> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getOperations)) {
            statement.setInt(1, allOperationsDTO.getUserId());
            statement.setInt(2, allOperationsDTO.getPageSize());
            ResultSet rs = statement.executeQuery();
            Mapper<OperationsData> operationMapper = new OperationMapper();
            while (rs.next())
                list.add(operationMapper.getEntity(rs));
        } catch (SQLException e) {
            LOG.error("SQLException occurred in BillPaymentJDBC.class at getLimitOperations() method");
        }
        AllOperationsDTO operationsDTO = new AllOperationsDTO();
        operationsDTO.setUserId(allOperationsDTO.getUserId());
        operationsDTO.setList(list);
        return operationsDTO;
    }

    @Override
    public BillPaymentOperation getById(int id) {
        Mapper<BillPaymentOperation> billPaymentMapper = new BillPaymentMapper();
        BillPaymentOperation billPaymentOperation = new BillPaymentOperation();
        billPaymentOperation.setUserId(-1);

        String getById = "SELECT * FROM bill_payment_operation WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                billPaymentOperation = billPaymentMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in BillPaymentJDBC.class at getById() method");
        }
        return billPaymentOperation;
    }
}
