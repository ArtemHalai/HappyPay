package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryption {

    public static String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
