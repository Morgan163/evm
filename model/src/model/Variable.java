package model;

import model.Matrix;

/**
 * Created by андрей on 04.11.2016.
 */
public class Variable {

    private String variableName;
    private Matrix matrix;

    public Variable(Matrix matrix, String variableName) {
        this.matrix = matrix;
        this.variableName = variableName;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

}
