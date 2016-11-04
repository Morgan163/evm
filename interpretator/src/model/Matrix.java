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

    public String toString(){
        StringBuilder out = new StringBuilder("[");
        for (int i =0;i<matrix.size();i++){
            out.append("[");
            createString(out,i,(ArrayList)matrix.get(i));
            out.append("]");
        }
        return out.toString();
    }

    public String createString(StringBuilder s, int i, ArrayList<Object> matr){
        if(matr.get(i) instanceof ArrayList){
            for(int j=0;j<((ArrayList) matr.get(i)).size();j++){
                s.append("[");
                createString(s, j,(ArrayList) matr.get(j));
                s.append("]");
            }
        }
        else{
            s.append("[")
                    .append(matr.get(i))
                    .append("]");
        }
        return s.toString();
    }
}
