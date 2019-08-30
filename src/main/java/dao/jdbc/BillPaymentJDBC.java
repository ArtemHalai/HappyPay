package dao.jdbc;

import dao.intefaces.BillPaymentDAO;
import dao.mappers.BillPaymentMapper;
import dao.mappers.Mapper;
import model.BillPaymentOperation;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillPaymentJDBC implements BillPaymentDAO {

    private static final Logger LOG = Logger.getLogger(BillPaymentJDBC.class);
    private Connection connection;

    public BillPaymentJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<BillPaymentOperation> getAllById(int id) {

        List<BillPaymentOperation> list = new ArrayList<>();

        String findAll = "SELECT * FROM bill_payment_operation WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            Mapper<BillPaymentOperation> billPaymentMapper = new BillPaymentMapper();
            while (rs.next()) {
                BillPaymentOperation billPaymentOperation = billPaymentMapper.getEntity(rs);
                list.add(billPaymentOperation);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in BillPaymentJDBC.class at getAllById() method");
        }
        return list;
    }


    @Override
    public boolean add(BillPaymentOperation billPaymentOperation) {

        String add = "INSERT INTO bill_payment_operation (user_id, bill_number, purpose, amount) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, billPaymentOperation.getUserId());
            statement.setString(2, billPaymentOperation.getBillNumber());
            statement.setString(3, billPaymentOperation.getPurpose());
            statement.setDouble(4, billPaymentOperation.getAmount());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in BillPaymentJDBC.class at add() method");
        }
        return false;
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

    @Override
    public List<BillPaymentOperation> findAll() {
        List<BillPaymentOperation> list = new ArrayList<>();

        String findAll = "SELECT * FROM bill_payment_operation";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            ResultSet rs = statement.executeQuery();

            Mapper<BillPaymentOperation> billPaymentMapper = new BillPaymentMapper();

            while (rs.next()) {
                BillPaymentOperation billPaymentOperation = billPaymentMapper.getEntity(rs);
                list.add(billPaymentOperation);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in BillPaymentJDBC.class at findAll() method");
        }
        return list;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in BillPaymentJDBC.class at close() method");
        }
    }
}
