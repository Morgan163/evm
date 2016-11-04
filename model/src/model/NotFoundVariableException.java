package model;

/**
 * Created by андрей on 04.11.2016.
 */
public class NotFoundVariableException extends Exception {

    public NotFoundVariableException(String message) {
        super(message);
    }

    public NotFoundVariableException(String message, Throwable cause) {
        super(message, cause);
    }
}
