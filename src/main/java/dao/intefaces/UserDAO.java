package dao.intefaces;

import model.User;

public interface UserDAO extends DAO<User> {

    int add(User user);

    boolean isUserExist(String username);

    User getUserByUsernameAndPassword(User user);

    int getUserIdByUsername(String username);
}
