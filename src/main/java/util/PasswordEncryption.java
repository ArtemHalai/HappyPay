package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Define a class to encrypt password.
 */
public class PasswordEncryption {

    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Method to check if user's password is compatible with already hashed password.
     *
     * @param password The password.
     * @param hashed The hashed password.
     * @return <code>true</code> if user's password is equal to hashed password; <code>false</code> otherwise.
     */
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
