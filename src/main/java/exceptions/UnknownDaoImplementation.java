package exceptions;

public class UnknownDaoImplementation extends RuntimeException {

    public UnknownDaoImplementation(String message) {
        super(message);
    }
}
