package dao.jdbc;

import dao.intefaces.ClientDetailsDAO;
import dao.mappers.ClientDetailsMapper;
import dao.mappers.Mapper;
import model.ClientDetails;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDetailsJDBC implements ClientDetailsDAO {

    private static final Logger LOG = Logger.getLogger(ClientDetailsJDBC.class);
    private Connection connection;

    public ClientDetailsJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(ClientDetails clientDetails) {

        String add = "INSERT INTO client_details (user_id, name, last_name, mobile_phone, username, " +
                "birthday) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, clientDetails.getUserId());
            statement.setString(2, clientDetails.getName());
            statement.setString(3, clientDetails.getLastName());
            statement.setString(4, clientDetails.getMobilePhone());
            statement.setString(5, clientDetails.getUsername());
            statement.setDate(6, clientDetails.getBirthday());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            LOG.error("SQLException occurred in ClientDetailsJDBC.class at add() method");
        }
        return false;
    }

    @Override
    public ClientDetails getById(int id) {
        Mapper<ClientDetails> clientDetailsMapper = new ClientDetailsMapper();
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setUserId(-1);

        String getById = "SELECT * FROM client_details WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(getById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                clientDetails = clientDetailsMapper.getEntity(rs);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in ClientDetailsJDBC.class at getById() method");
        }
        return clientDetails;
    }

    @Override
    public List<ClientDetails> findAll() {
        List<ClientDetails> list = new ArrayList<>();

        String findAll = "SELECT * FROM client_details";

        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            ResultSet rs = statement.executeQuery();

            Mapper<ClientDetails> clientDetailsMapper = new ClientDetailsMapper();

            while (rs.next()) {
                ClientDetails clientDetails = clientDetailsMapper.getEntity(rs);
                list.add(clientDetails);
            }
        } catch (SQLException e) {
            LOG.error("SQLException occurred in ClientDetailsJDBC.class at findAll() method");
        }
        return list;
    }

    @Override
    public void close() {
        try {
            connection.close();
            LOG.debug("Connection closed");
        } catch (SQLException e) {
            LOG.error("SQLException occurred in ContactDetailsJDBC.class from close() method", e);
            throw new RuntimeException(e);
        }
    }
}
