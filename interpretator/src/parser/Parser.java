/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;

/**
 *
 * @author Краснопольский
 */
public class Parser {
    static int globalIndexHelper = 0;
    public static Object getTokenFromString(StringBuilder s, int startIndex, Object result) throws InvalidStringFormatException{
        while(true){
            if( s.charAt(startIndex) == ']')
                 return result;
            else if( s.charAt(startIndex) == '[') {
                    ArrayList list = (ArrayList)result;
                    Object nextResult = null;
                    if (s.charAt(startIndex + 1) == '[') {
                        nextResult = new ArrayList();
                    }
                    else if (Character.isDigit(s.charAt(startIndex + 1))) {
                        nextResult = new Integer(0);
                    }
                    startIndex += 1;                
                    list.add(getTokenFromString(s, startIndex, nextResult));
                    startIndex = globalIndexHelper;
                }
            else if (Character.isDigit(s.charAt(startIndex))) {
                globalIndexHelper = startIndex;
                StringBuilder number = new StringBuilder("");
                while(s.charAt(globalIndexHelper) != ']'){
                    number.append(s.charAt(globalIndexHelper));
                    globalIndexHelper++;
                }
                return Integer.valueOf(number.toString());            
            }
            else
                throw new InvalidStringFormatException(s.toString(),startIndex);
            }
        
    }
}
