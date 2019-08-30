package exceptions;

public class WrongUsernameOrPasswordException extends RuntimeException {

    public WrongUsernameOrPasswordException(String message) {
        super(message);
    }
}
