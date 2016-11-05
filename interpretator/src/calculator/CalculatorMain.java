package calculator;

import exceptions.CannotPossiblyCalculateException;
import exceptions.InvalidStringFormatException;
import model.Matrix;
import parser.Parser;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by andrei on 05.11.16.
 */
public class CalculatorMain {
    public static void main(String[] args) {
        System.out.println("");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        ArrayList<Object> list = new ArrayList<>();
        ArrayList<Object> list2 = new ArrayList<>();
        Calculator calc = new Calculator();
        try {
            Matrix m = new Matrix((ArrayList<Object>) Parser.getTokenFromString(new StringBuilder(s), 0, list));
            Matrix c = new Matrix((ArrayList<Object>) Parser.getTokenFromString(new StringBuilder(s), 0, list2));
            Matrix res = calc.minus(m,c);
            System.out.println(res.toString());
        } catch (InvalidStringFormatException ex) {
            ex.printStackTrace();
        }catch (CannotPossiblyCalculateException ex){
            ex.printStackTrace();
        }
    }
}
