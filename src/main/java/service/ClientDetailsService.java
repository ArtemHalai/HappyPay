package service;

import dao.intefaces.ClientDetailsDAO;
import factories.DaoFactory;
import model.ClientDetails;
import util.PasswordEncryption;

import java.sql.Connection;

import static enums.DAOEnum.CLIENT_DETAILS_JDBC;

public class ClientDetailsService {

    private ClientDetailsDAO clientDetailsDAO;
    private DaoFactory factory;

    public ClientDetailsService() {
        factory = DaoFactory.getInstance();
    }

    public void setClientDetailsDAO(ClientDetailsDAO clientDetailsDAO) {
        this.clientDetailsDAO = clientDetailsDAO;
    }

    public void setDefaultClientDetailsDAO(Connection connection) {
        this.clientDetailsDAO = factory.getClientDetailsDAO(connection, CLIENT_DETAILS_JDBC);
    }

    public boolean add(ClientDetails clientDetails) {
        String password = clientDetails.getPassword();
        clientDetails.setPassword(PasswordEncryption.encryptPassword(password));
        return clientDetailsDAO.add(clientDetails);
    }

    public ClientDetails getById(int id) {
        return clientDetailsDAO.getById(id);
    }
}
