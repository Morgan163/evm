/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import calculator.Calculator;
import exceptions.CannotPossiblyCalculateException;
import exceptions.IndefinedVariableException;
import exceptions.InvalidStringFormatException;
import exceptions.NotFoundVariableException;
import exceptions.OutOfMatrixBoundsException;
import exceptions.UnsupportedSymbolException;
import exceptions.SymbolExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import model.Matrix;
import model.VariablesContainer;

/**
 *
 * @author Краснопольский
 */
public class Parser {
    static int globalIndexHelper = 0;
    static boolean integerTypeHelper = false;
    static HashMap<Character, int[]> weights;
    {
        weights = new HashMap<>();
        weights.put('(', new int[]{100, 0});
        weights.put(')', new int[]{0, -1});
        weights.put('+', new int[]{2, 2});
        weights.put('-', new int[]{2, 2});
        weights.put('*', new int[]{3, 3});
        weights.put('/', new int[]{3, 3});
    }
    
    static HashMap<Character, String> operands;
    
    
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
    
    public static StringBuilder modifyRightPart(String s) throws NotFoundVariableException, OutOfMatrixBoundsException, IndefinedVariableException, SymbolExpectedException{
        StringBuilder right = new StringBuilder("");
        ArrayList<String> container  = new ArrayList<>();
        if (s.contains(":=")) {
            right = new StringBuilder(s.split(":=")[1].trim());
            //replace all vars
            VariablesContainer vars = VariablesContainer.getVariablesContainer();
            for (int i = 0; i < right.length(); i++) {
                StringBuilder variable = new StringBuilder("");
                while( i < right.length() && Character.isLetter(right.charAt(i))){
                    variable.append(right.charAt(i));
                    i++;
                }
                if (!variable.toString().equals("")) {
                    int[] indexes = null;
                    if (vars.isVariableExist(variable.toString())) {
                        if ( i < right.length() && right.charAt(i) == '[') {
                            int j = i;
                            while(j < right.length() && right.charAt(j) != '+' && right.charAt(j) != '-' && right.charAt(j) != '*' ){
                                j++;
                            }
                            j--;
                            indexes = getIndexes(right, i, j);
                            Object obj = vars.getVariableByName(variable.toString()).getMatrix().getObjectByIndex(indexes);                           
                            
                            right.replace(i - variable.length(), j, (new Matrix((ArrayList)obj)).toString());
                            
                        }
                        else{
                            Object obj = vars.getVariableByName(variable.toString()).getMatrix().getMatrix();                           
                            right.replace(i - variable.length(), i, (new Matrix((ArrayList)obj)).toString());
                            
                        }
                    }
                    else{
                        throw new IndefinedVariableException(variable.toString());
                    }
                }
            }
        }
        
        return right;
    }
    
    public static int[] getIndexes(StringBuilder s, int i, int j) throws SymbolExpectedException{
        int start = i;
        int end = j;
        ArrayList<Integer> list = new ArrayList<>();
        StringBuilder index = new StringBuilder("");
        while(start <= end){
            if(Character.isDigit(s.charAt(start))) {
                index.append(s.charAt(start));
            }
            if (s.charAt(start) == ']') {
                if (!index.toString().equals("")) {
                    list.add(Integer.valueOf(index.toString()));
                    index = new StringBuilder("");
                }
            }
            start++;
            
        }
        if (!index.toString().equals("")) {
            throw new SymbolExpectedException(']');
        }
        int[] indexes = new int[list.size()];
        for (int k = 0; k < indexes.length; k++) {
            indexes[k] = list.get(k);
        }
        return indexes;
            
    }
    
    public static String getPOLIZ(StringBuilder s) throws InvalidStringFormatException, SymbolExpectedException{
        if (!(s.toString().contains("*") || s.toString().contains("+") || s.toString().contains("-"))) {
            return s.toString();
        }
        if(weights == null){
              weights = new HashMap<>();
        weights.put('(', new int[]{100, 0});
        weights.put(')', new int[]{0, -1});
        weights.put('+', new int[]{2, 2});
        weights.put('-', new int[]{2, 2});
        weights.put('*', new int[]{3, 3});
        weights.put('/', new int[]{3, 3});
        }
        StringBuilder poliz = new StringBuilder("");
        
        ArrayList<Character> stack = new ArrayList<>();
        int pos = 0;
        
        StringBuilder operand = new StringBuilder("");
        int code = 65;
        
        operands = new HashMap<>();
        int i = 0;
        while(i < s.length()){
            switch(s.charAt(i)){
                case '-': {
                    if (!operand.toString().equals("")) {
                        operands.put((char) code, operand.toString());
                        operand = new StringBuilder("");
                        poliz.append((char)code);
                        code++;
                    }
                    else{ 
                        throw new InvalidStringFormatException(s.toString(), i);
                    }
                    if (pos == 0) {
                        stack.add('-');
                        pos++;
                    }
                    else if (weights.get('-')[0] > weights.get(stack.get(pos-1))[1]) {
                        stack.add('-');
                        pos++;
                    }
                    else {
                        while((pos >0) && weights.get('-')[0] <= weights.get(stack.get(pos-1))[1]){
                            pos--;
                            poliz.append(stack.get(pos).toString());
                            stack.remove(pos);
                           
                        }
                        stack.add('-');
                        pos++;
                    }
                    break;
                }
                case '+':{
                    if (!operand.toString().equals("")) {
                        operands.put((char) code, operand.toString());
                        operand = new StringBuilder("");
                        poliz.append((char)code);
                        code++;
                    }
                    else{ 
                        throw new InvalidStringFormatException(s.toString(), i);
                    }
                    
                    if (pos == 0) {
                        stack.add('+');
                        pos++;
                    }
                    else if (weights.get('+')[0] > weights.get(stack.get(pos-1))[1]) {
                        stack.add('+');
                        pos++;
                    }
                    else {
                        while((pos >0) && weights.get('+')[0] <= weights.get(stack.get(pos-1))[1]){
                             pos--;
                            poliz.append(stack.get(pos).toString());
                            stack.remove(pos);
                           
                        }
                        stack.add('+');
                        pos++;
                    }
                    break;
                }
                case '*': {
                    if (!operand.toString().equals("")) {
                        operands.put((char) code, operand.toString());
                        operand = new StringBuilder("");
                        poliz.append((char)code);
                        code++;
                    }
                    else{ 
                        throw new InvalidStringFormatException(s.toString(), i);
                    }
                    
                    if (pos == 0) {
                        stack.add('*');
                        pos++;
                    }
                    else if (weights.get('*')[0] > weights.get(stack.get(pos-1))[1]) {
                        stack.add('*');
                        pos++;
                    }
                    else {
                        while((pos >0) && weights.get('*')[0] <= weights.get(stack.get(pos-1))[1]){
                            pos--;
                            poliz.append(stack.get(pos).toString());
                            stack.remove(pos);
                            
                        }
                        stack.add('*');
                        pos++;
                    }
                    break;
                }
                
                case '(': {
                    if (!operand.toString().equals("")) {
                        operands.put((char) code, operand.toString());
                        operand = new StringBuilder("");
                        poliz.append((char)code);
                        code++;
                    }
                    else{ 
                        throw new InvalidStringFormatException(s.toString(), i);
                    }
                    
                    if (pos == 0) {
                        stack.add('(');
                        pos++;
                    }
                    else if (weights.get('(')[0] > weights.get(stack.get(pos-1))[1]) {
                        stack.add('(');
                        pos++;
                    }
                    else {
                        while((pos >0) && weights.get('(')[0] <= weights.get(stack.get(pos-1))[1]){
                            pos--;
                            poliz.append(stack.get(pos).toString());
                            stack.remove(pos);
                            
                        }
                        stack.add('(');
                        pos++;
                    }
                    break;
                }
                case ')': {
                    if (!operand.toString().equals("")) {
                        operands.put((char) code, operand.toString());
                        operand = new StringBuilder("");
                        poliz.append((char)code);
                        code++;
                    }
                    else{ 
                        throw new InvalidStringFormatException(s.toString(), i);
                    }
                    while(pos > 0 && !stack.get(pos-1).equals('('))
                    {   
                        pos--;
                        poliz.append(stack.get(pos));
                        stack.remove(i);
                        
                    }
                    if (pos >= 0 && stack.get(pos-1).equals('(')) {
                        stack.remove('(');
                    }
                    break;
                }
                default : {
                    operand.append(s.charAt(i));
                }
            }
            i++;
        }
        if (!operand.toString().equals("")) {
                        operands.put((char) code, operand.toString());
                        operand = new StringBuilder("");
                        poliz.append((char)code);
                        code++;
                    }
        pos--;
        while (!stack.isEmpty()) {
            if(stack.get(pos).equals('(')){
                throw new SymbolExpectedException(')'); 
            }
            poliz.append(stack.get(pos));
            stack.remove(pos);
            pos--;
        }
        return poliz.toString();
    }
    
    public static Matrix getMatrixFromPoliz(String poliz) throws InvalidStringFormatException, CannotPossiblyCalculateException, UnsupportedSymbolException{
         if (!(poliz.toString().contains("*") || poliz.toString().contains("+") || poliz.toString().contains("-"))) {
            ArrayList<Object> matrix = new ArrayList<>();
            Matrix result = new Matrix( (ArrayList<Object>) getTokenFromString(new StringBuilder(poliz), 0, matrix));
            return result;
        }
            int i = 0;
        
        ArrayList<Matrix> stack = new ArrayList<>();
        int pos = 0;
        Calculator calc = new Calculator();
        while(i < poliz.length()){
            if (Character.isLetter(poliz.charAt(i))) {
                Matrix operand;
                ArrayList<Object> matrix = new ArrayList<>();
                String token;
                token = operands.get(poliz.charAt(i));
                operand = new Matrix( (ArrayList<Object>) getTokenFromString(new StringBuilder(token), 0, matrix));
                stack.add(operand);
                pos++;
            }
            else {
                pos--;
                Matrix operand1 = stack.get(pos);
                stack.remove(pos);
                pos--;
                Matrix operand2 = stack.get(pos);
                stack.remove(pos);
                
                Matrix result = null;
                switch(poliz.charAt(i)){
                    case '+':{
                        result = calc.plus(operand2, operand1);
                        break;
                    }
                    case '-':{
                        result = calc.minus(operand2, operand1);
                        break;
                    }
                        
                    case '*':{
                        result = calc.multiply(operand2, operand1);
                        break;
                    }
                }
                if (result == null) {
                    throw new UnsupportedSymbolException(poliz.charAt(i));
                }
                stack.add(result);
                pos++;
            }
            i++;
        }
        return stack.get(0);
    }


}