package exceptions;

/**
 * An exception which extends RuntimeException and provides information that unknown dao implementation
 * has been used.
 *
 * @see RuntimeException
 */
public class UnknownDaoImplementation extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message. The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param   message The detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public UnknownDaoImplementation(String message) {
        super(message);
    }
}
