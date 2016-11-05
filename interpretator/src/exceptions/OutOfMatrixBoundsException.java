package exceptions;

/**
 * Created by andrei on 05.11.16.
 */
public class OutOfMatrixBoundsException extends Exception {
    public OutOfMatrixBoundsException(String message) {
        super(message);
    }

    public OutOfMatrixBoundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
