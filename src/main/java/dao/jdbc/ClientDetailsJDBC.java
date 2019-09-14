package dao.jdbc;

import dao.intefaces.ClientDetailsDAO;
import dao.mappers.ClientDetailsMapper;
import dao.mappers.Mapper;
import model.ClientDetails;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClientDetailsJDBC implements ClientDetailsDAO {

    private static final Logger LOG = Logger.getLogger(ClientDetailsJDBC.class);
    private Connection connection;

    public ClientDetailsJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean add(ClientDetails clientDetails) {

        String add = "INSERT INTO client_details (user_id, name, surname, phone_number, username, password," +
                "birthday) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(add)) {
            statement.setInt(1, clientDetails.getUserId());
            statement.setString(2, clientDetails.getName());
            statement.setString(3, clientDetails.getSurname());
            statement.setString(4, clientDetails.getPhoneNumber());
            statement.setString(5, clientDetails.getUsername());
            statement.setString(6, clientDetails.getPassword());
            statement.setDate(7, clientDetails.getBirthday(), Calendar.getInstance());
            int generated = statement.executeUpdate();
            if (generated > 0)
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
}
