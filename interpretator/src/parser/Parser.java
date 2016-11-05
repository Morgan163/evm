/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import exceptions.InvalidStringFormatException;

import java.util.ArrayList;

/**
 *
 * @author Краснопольский
 */
public class Parser {
    static int globalIndexHelper = 0;
    static boolean integerTypeHelper = false;
    
    public static Object getTokenFromString(StringBuilder s, int startIndex, Object result) throws InvalidStringFormatException {
        globalIndexHelper = startIndex;
        while(true){
            if(globalIndexHelper >= s.length())
                return result;
            if(s.charAt(globalIndexHelper) == ']'){
                globalIndexHelper++;
                integerTypeHelper = false;
                 return result;
            }
            else if( s.charAt(globalIndexHelper) == '[') {
                    ArrayList list = (ArrayList)result;
                    Object nextResult = null;
                    if (s.charAt(globalIndexHelper + 1) == '[') {
                        nextResult = new ArrayList();
                        integerTypeHelper = false;
                    }
                    else if (Character.isDigit(s.charAt(globalIndexHelper + 1))) {
                        nextResult = new Integer(0);
                        integerTypeHelper = true;
                    }
                    globalIndexHelper++;                
                    list.add(getTokenFromString(s, globalIndexHelper, nextResult));
                    if(integerTypeHelper) globalIndexHelper++;
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
