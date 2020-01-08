package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryption {
    private static final String SALT = "$2$11!1812gH1234yU4321Ea54321";

    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, SALT);
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
