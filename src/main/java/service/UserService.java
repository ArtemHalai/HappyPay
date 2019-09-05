package service;

import dao.intefaces.UserDAO;
import model.User;
import util.PasswordEncryption;

import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserByUsernameAndPassword(User user) {
        encryptPassword(user);
        return userDAO.getUserByUsernameAndPassword(user);
    }

    public boolean isUserExist(String username) {
        return userDAO.isUserExist(username);
    }

    public int getUserIdByUsername(String username) {
        return userDAO.getUserIdByUsername(username);
    }

    public int addUser(User user) {
        encryptPassword(user);
        return userDAO.add(user);
    }

    public User getUserById(int id) {
        return userDAO.getById(id);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    private void encryptPassword(User user) {
        String password = user.getPassword();
        user.setPassword(PasswordEncryption.encryptPassword(password));
    }
}
