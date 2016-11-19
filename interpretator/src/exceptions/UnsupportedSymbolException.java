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
public class UnsupportedSymbolException extends Exception{

    public UnsupportedSymbolException(char ch) {
        super("Unsopperted symbol "+ ch);
    }
    
    
}
