package exceptions;

public class WrongUsernameOrPasswordException extends Exception {

    public WrongUsernameOrPasswordException(String message) {
        super(message);
    }
}
