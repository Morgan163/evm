package main;

import exceptions.*;
import model.Matrix;
import model.Variable;
import model.VariablesContainer;
import parser.Parser;

import java.util.Scanner;

/**
 * Created by андрей on 19.11.2016.
 */
public class main {
    public static void main(String[] args) {
        String inputString = "";
        Scanner scanner = new Scanner(System.in);
        inputString = scanner.nextLine();
        VariablesContainer variablesContainer = VariablesContainer.getVariablesContainer();
        while (!inputString.equals("EXIT")){
            if(inputString.contains(":=")){
                try {
                    StringBuilder rightPart = Parser.modifyRightPart(inputString);
                    Matrix result = Parser.getMatrixFromPoliz(Parser.getPOLIZ(rightPart));
                    Variable variable = new Variable(result, (inputString.split(":="))[0]);
                    variablesContainer.addVariable(variable);
                } catch (NotFoundVariableException | OutOfMatrixBoundsException | IndefinedVariableException |
                        SymbolExpectedException| InvalidStringFormatException| UnsupportedSymbolException |
                        CannotPossiblyCalculateException e) {
                    System.out.println(e.getMessage());
                }
            }else if(inputString.contains("=")){
                String variableName = (inputString.split("="))[0];
                try {
                    Variable variable = variablesContainer.getVariableByName(variableName);
                    System.out.println(variable.getMatrix().toString());
                } catch (NotFoundVariableException e) {
                    System.out.println(e.getMessage());
                }
            }else{
                System.out.println("Not found '=' or ':=' operators");
            }

            inputString = scanner.nextLine();

        }
    }
}
