package service;

import dao.intefaces.UserDAO;
import model.User;
import util.PasswordEncryption;

import static enums.Role.ADMIN;

/**
 * A class that works with UserDAO.
 *
 * @see UserDAO
 */
public class UserService {

    private UserDAO userDAO;

    /**
     * Method to set UserDAO object {@link #userDAO}.
     *
     * @param userDAO The UserDAO object.
     * @see UserDAO
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Method to get User by username and password.
     *
     * @param user The User object.
     * @return The User object.
     */
    public User getUserByUsernameAndPassword(User user) {
        User u = userDAO.isUserExist(user.getUsername());
        if (u != null) {
            if (u.getRole() == ADMIN.getRoleId()) {
                return userDAO.getUserByUsernameAndPassword(user);
            }
            boolean exist = PasswordEncryption.checkPassword(user.getPassword(), u.getPassword());
            if (exist)
                return u;
        }
        return null;
    }

    /**
     * Method to check if user exists by username.
     *
     * @param username The username of user.
     * @return <code>true</code> if user exists; <code>false</code> otherwise.
     */
    public boolean isUserExist(String username) {
        return userDAO.isUserExist(username) != null;
    }

    /**
     * Method to add user.
     *
     * @param user The User object.
     * @return The int value representing the auto-generated id in database.
     * @see User
     */
    public int addUser(User user) {
        encryptPassword(user);
        return userDAO.add(user);
    }

    /**
     * Method to encrypt password.
     *
     * @param user The User object.
     * @return The User object with encrypted password.
     * @see User
     */
    private User encryptPassword(User user) {
        String password = user.getPassword();
        user.setPassword(PasswordEncryption.encryptPassword(password));
        return user;
    }
}
