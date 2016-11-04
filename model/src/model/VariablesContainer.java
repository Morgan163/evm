package model;

import java.util.ArrayList;

/**
 * Created by андрей on 04.11.2016.
 */
public class VariablesContainer {
    private ArrayList<Variable> variables = new ArrayList<Variable>();
    private static VariablesContainer variablesContainer = new VariablesContainer();

    public static VariablesContainer getVariablesContainer(){
        return variablesContainer;
    }
    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void addVariable(Variable variable){
        variables.add(variable);
    }

    public Variable getVariableByIndex(int index){
        return variables.get(index);
    }

    public Variable getVariableByName(String variableName) throws NotFoundVariableException {
        Variable foundVariable = null;
        for (Variable variable:variables){
            if(variable.getVariableName().equals(variableName)){
                foundVariable =  variable;
            }
        }
        if(foundVariable==null){
            throw  new NotFoundVariableException("model.Variable by name not found");
        }else{
            return foundVariable;
        }
    }

    public void setVariable(Variable newVariable) throws NotFoundVariableException {
        boolean isFound = false;
        for (int i=0;i<variables.size();i++){
            if(variables.get(i).getVariableName().equals(newVariable.getVariableName())){
                isFound = true;
                variables.set(i,newVariable);
            }
        }
        if(!isFound){
            throw  new NotFoundVariableException("model.Variable by name not found");
        }
    }

    public int length(){
        return variables.size();
    }

    public void removeVariableByName(String variableName) throws NotFoundVariableException {
        boolean isFound = false;
        for (int i=0;i<variables.size();i++){
            if(variables.get(i).getVariableName().equals(variableName)){
                isFound = true;
                variables.remove(i);
            }
        }
        if(!isFound){
            throw  new NotFoundVariableException("model.Variable by name not found");
        }
    }






}
