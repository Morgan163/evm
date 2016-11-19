package model;

import exceptions.OutOfMatrixBoundsException;

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

    public int length() {
        return matrix.size();
    }

    public Object getObjectByIndex(int[] indexs) throws OutOfMatrixBoundsException {
        Object result = null;
        if (indexs[0] < matrix.size()) {
            result = matrix.get(indexs[0]);
        } else {
            throw new OutOfMatrixBoundsException("invalid index");
        }

        for (int i = 1; i < indexs.length; i++) {
            if (i < ((ArrayList) result).size()) {
                if (((ArrayList) result).get(i) instanceof ArrayList) {
                    result = ((ArrayList) result).get(i);
                } else {
                    throw new OutOfMatrixBoundsException("invalid index");
                }
            } else {
                throw new OutOfMatrixBoundsException("invalid index");
            }
        }
        return result;
    }

    public String toString() {
        StringBuilder out = new StringBuilder("");
        for (int i = 0; i < matrix.size(); i++) {
            out.append("[");
            if (matrix.get(i) instanceof ArrayList) {
                createString(out, matrix.get(i));
            } else {
                out.append(matrix.get(i));
            }
            out.append("]");
        }
        return out.toString();
    }

    private void createString(StringBuilder s, Object matr) {
        if (matr instanceof ArrayList) {
            for (int j = 0; j < ((ArrayList) matr).size(); j++) {
                s.append("[");
                createString(s, ((ArrayList) matr).get(j));
                s.append("]");
            }
        } else {
            s.append(matr);
        }
    }
}
