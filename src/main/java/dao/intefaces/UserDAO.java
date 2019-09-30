package dao.intefaces;

import model.User;

/**
 * Define a data access object used for executing user's requests to database.
 */
public interface UserDAO extends DAO<User> {

    /**
     * Method to add user.
     *
     * @param user The User object.
     * @return The int value representing the auto-generated id in database.
     * @see User
     */
    int add(User user);

    /**
     * Method to get user by username.
     *
     * @param username The username of user.
     * @return The User object.
     * @see User
     */
    User isUserExist(String username);

    /**
     * Method to get user by username and password.
     *
     * @param user The User object.
     * @return The User object.
     * @see User
     */
    User getUserByUsernameAndPassword(User user);
}
