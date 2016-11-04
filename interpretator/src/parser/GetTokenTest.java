/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Matrix;

/**
 *
 * @author Краснопольский
 */
public class GetTokenTest {
    public static void main(String[] args) {
        System.out.println("");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        ArrayList<Object> list = new ArrayList<>();
         
        try {
            Matrix m = new Matrix((ArrayList<Object>) Parser.getTokenFromString(new StringBuilder(s), 0, list));
            System.out.println(m.toString());
        } catch (InvalidStringFormatException ex) {
            ex.printStackTrace();
        }
        
    }
}
