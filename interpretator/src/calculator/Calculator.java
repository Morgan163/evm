package calculator;

import exceptions.CannotPossiblyCalculateException;
import model.Matrix;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by андрей on 04.11.2016.
 */
public class Calculator {
    
    public Matrix plus(Matrix a, Matrix b) throws CannotPossiblyCalculateException {
       return calculatePlusOrMinus(a,b,"+");
    }

    public Matrix minus(Matrix a, Matrix b) throws CannotPossiblyCalculateException {
       return calculatePlusOrMinus(a,b,"-");
    }

    public Matrix multiply (Matrix a, Matrix b){
        Matrix result = null;
        ArrayList<Object> matrA = a.getMatrix();
        ArrayList<Object> matrB = b.getMatrix();
        calculateMultiply(matrA, matrB);
        result = new Matrix(matrA);
        return result;
    }

    private void calculateMultiply(Object a, Object b){
        for (int i = 0; i< ((ArrayList)a).size();i++) {
            if (((ArrayList) a).get(i) instanceof ArrayList){
                calculateMultiply(((ArrayList) a).get(i),b);
            }else{
                ArrayList<Object> c = new ArrayList<>();
                cloneArrayList(c,(ArrayList)b);
                matrixMultiplicationByNumber((int)((ArrayList) a).get(i), c);
                ((ArrayList) a).set(i,c);
            }
        }
    }

    private void matrixMultiplicationByNumber(int a, Object b){
        for (int i = 0; i< ((ArrayList)b).size();i++) {
            if (((ArrayList) b).get(i) instanceof ArrayList){
                matrixMultiplicationByNumber(a,((ArrayList) b).get(i));
            }else{
               ((ArrayList) b).set(i,(Integer)((ArrayList) b).get(i)*a);
            }
        }
    }

    private void calculate(Object a, Object b, String symbol) throws CannotPossiblyCalculateException {
        if((a instanceof ArrayList)&&(b instanceof  ArrayList)) {
            if (((ArrayList) a).size() == ((ArrayList) b).size()) {
                for (int j = 0; j < ((ArrayList) a).size(); j++) {
                    calculate(((ArrayList) a).get(j), ((ArrayList) b).get(j), symbol);
                }
            }
            else{
                throw new CannotPossiblyCalculateException("Different matrix size");
            }
        }
        else if((a instanceof Integer)&&(b instanceof  Integer)){
            switch (symbol){
                case "+":
                    a=(Integer)a+(Integer)b;
                    break;
                case "-":
                    a=(Integer)a-(Integer)b;
            }
        }
    }

    private Matrix calculatePlusOrMinus(Matrix a, Matrix b, String symbol) throws CannotPossiblyCalculateException {
        Matrix result = new Matrix(a.getMatrix());
        if(a.length()==b.length()){
            ArrayList<Object> res = result.getMatrix();
            ArrayList<Object> matrB = b.getMatrix();
            for (int i=0;i<res.size();i++){
                if(res.get(i) instanceof ArrayList) {
                    calculate(res.get(i), matrB.get(i), "+");
                }else{
                    res.set(i,(Integer)res.get(i)+(Integer)matrB.get(i));
                }
            }
            result.setMatrix(res);
        }
        return result;
    }

    private void cloneArrayList(ArrayList<Object> c,ArrayList<Object> b){
        for (int i=0;i<b.size();i++){
            if(b.get(i) instanceof ArrayList){
                c.add(b.get(i));
                cloneArrayList((ArrayList)c.get(i), (ArrayList)b.get(i));
            }else{
                c.add(new Integer((int)b.get(i)));
            }
        }

    }
}
