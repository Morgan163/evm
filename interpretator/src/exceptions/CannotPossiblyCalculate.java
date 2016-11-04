package exceptions;

/**
 * Created by андрей on 04.11.2016.
 */
public class CannotPossiblyCalculate extends Exception {
    public CannotPossiblyCalculate(String message) {
        super(message);
    }

    public CannotPossiblyCalculate(String message, Throwable cause) {
        super(message, cause);
    }
}
