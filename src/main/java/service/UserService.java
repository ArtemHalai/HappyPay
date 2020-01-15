package service;

import dao.intefaces.UserDAO;
import factories.DaoFactory;
import model.User;
import util.PasswordEncryption;

import java.sql.Connection;

import static enums.DAOEnum.USER_JDBC;

public class UserService {

    private UserDAO userDAO;
    private DaoFactory factory;

    public UserService() {
        factory = DaoFactory.getInstance();
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setDefaultUserDAO(Connection connection) {
        this.userDAO = factory.getUserDAO(connection, USER_JDBC);
    }

    public User getUserByUsernameAndPassword(String username, String password) {

        User userExist = userDAO.isUserExist(username);
        if (userExist != null) {
            boolean exist = PasswordEncryption.checkPassword(password, userExist.getPassword());
            if (exist) {
                return userExist;
            }
        }
        return null;
    }

    public boolean isUserExist(String username) {
        return userDAO.isUserExist(username) != null;
    }

    public int addUser(User user) {
        encryptPassword(user);
        return userDAO.add(user);
    }

    private User encryptPassword(User user) {
        String password = user.getPassword();
        user.setPassword(PasswordEncryption.encryptPassword(password));
        return user;
    }
}
