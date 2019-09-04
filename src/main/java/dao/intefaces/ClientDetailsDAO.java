package dao.intefaces;

import model.ClientDetails;

public interface ClientDetailsDAO extends DAO<ClientDetails> {

    boolean add(ClientDetails clientDetails);
}
