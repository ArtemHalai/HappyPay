package service;

import dao.intefaces.ClientDetailsDAO;
import model.ClientDetails;
import util.PasswordEncryption;

/**
 * A class that works with ClientDetailsDAO.
 *
 * @see ClientDetailsDAO
 */
public class ClientDetailsService {

    private ClientDetailsDAO clientDetailsDAO;

    /**
     * Method to set ClientDetailsDAO object {@link #clientDetailsDAO}.
     *
     * @param clientDetailsDAO The ClientDetailsDAO object.
     * @see ClientDetailsDAO
     */
    public void setClientDetailsDAO(ClientDetailsDAO clientDetailsDAO) {
        this.clientDetailsDAO = clientDetailsDAO;
    }

    /**
     * Method to add ClientDetails object.
     *
     * @param clientDetails The ClientDetails object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see ClientDetails
     */
    public boolean add(ClientDetails clientDetails) {
        String password = clientDetails.getPassword();
        clientDetails.setPassword(PasswordEncryption.encryptPassword(password));
        return clientDetailsDAO.add(clientDetails);
    }

    /**
     * Method to get ClientDetails object by user id.
     *
     * @param id The user id.
     * @return The ClientDetails object.
     * @see ClientDetails
     */
    public ClientDetails getById(int id) {
        return clientDetailsDAO.getById(id);
    }
}
