package dao.intefaces;

import model.ClientDetails;

/**
 * Define a data access object used for executing client details requests to database.
 */
public interface ClientDetailsDAO extends DAO<ClientDetails> {

    /**
     * Method to add client details.
     *
     * @param clientDetails The ClientDetails object.
     * @return <code>true</code> if client details was added; <code>false</code> otherwise.
     * @see ClientDetails
     */
    boolean add(ClientDetails clientDetails);
}
