package model;

import java.util.ArrayList;

/**
 * Created by андрей on 04.11.2016.
 */
public class Matrix {
    private ArrayList<Object> matrix;

    public Matrix(ArrayList<Object> matrix) {
        this.matrix = matrix;
    }

    public ArrayList<Object> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<Object> matrix) {
        this.matrix = matrix;
    }

    public int length(){
        return matrix.size();
    }
}
