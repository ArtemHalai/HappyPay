package dao.intefaces;

import model.User;

public interface UserDAO extends DAO<User> {

    boolean isUserExist(String username);

    User getUserByUsernameAndPassword(String username, String password);

    int getUserIdByUsername(String username);
}
