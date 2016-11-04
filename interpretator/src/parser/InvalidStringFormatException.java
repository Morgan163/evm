/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Краснопольский
 */
public class InvalidStringFormatException extends Exception{

    public InvalidStringFormatException(String s, int index) {
        super("invalid string format at " + s + " at index " + index);
    }
    

    public InvalidStringFormatException(String message) {
        super(message);
    }

    public InvalidStringFormatException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
