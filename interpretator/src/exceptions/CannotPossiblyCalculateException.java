package exceptions;

/**
 * Created by андрей on 04.11.2016.
 */
public class CannotPossiblyCalculateException extends Exception {
    public CannotPossiblyCalculateException(String message) {
        super(message);
    }

    public CannotPossiblyCalculateException(String message, Throwable cause) {
        super(message, cause);
    }
}
