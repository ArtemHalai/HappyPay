package service;

import dao.intefaces.UserDAO;
import model.User;
import util.PasswordEncryption;

public class UserService {

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
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
