/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Краснопольский
 */
public class IndefinedVariableException extends Exception{
      public IndefinedVariableException(String s) {
        super("Indefined Variable " + s);
    }
    
}
