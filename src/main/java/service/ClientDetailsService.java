package service;

import dao.intefaces.ClientDetailsDAO;
import model.ClientDetails;
import util.PasswordEncryption;

public class ClientDetailsService {

    private ClientDetailsDAO clientDetailsDAO;

    public void setClientDetailsDAO(ClientDetailsDAO clientDetailsDAO) {
        this.clientDetailsDAO = clientDetailsDAO;
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
