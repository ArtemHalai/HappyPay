package dao.intefaces;

import model.User;

public interface UserDAO extends DAO<User> {

    int add(User user);

    User isUserExist(String username);

    User getUserByUsernameAndPassword(User user);
}
