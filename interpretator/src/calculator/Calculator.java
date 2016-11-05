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
        ArrayList<Object> res = new ArrayList<>();
        calculateMultiply(res,matrA, matrB);
        result = new Matrix(res);
        return result;
    }

    private void calculateMultiply(Object res,Object a, Object b){
        for (int i = 0; i< ((ArrayList)a).size();i++) {
            ((ArrayList)res).add(new ArrayList<>());
            if (((ArrayList) a).get(i) instanceof ArrayList){
                calculateMultiply(((ArrayList) res).get(i),((ArrayList) a).get(i),b);
            }else{
                matrixMultiplicationByNumber(((ArrayList)res).get(i),(int)((ArrayList) a).get(i), b);
            }
        }
    }

    private void matrixMultiplicationByNumber(Object res,int a, Object b){
        for (int i = 0; i< ((ArrayList)b).size();i++) {
            if (((ArrayList) b).get(i) instanceof ArrayList){
                ((ArrayList)res).add(new ArrayList<>());
                matrixMultiplicationByNumber(((ArrayList) res).get(i),a,((ArrayList) b).get(i));
            }else{
               ((ArrayList) res).add((Integer)((ArrayList) b).get(i)*a);
            }
        }
    }

    private ArrayList<Object> calculate(ArrayList<Object>result,Object a, Object b, String symbol) throws CannotPossiblyCalculateException {
        if((a instanceof ArrayList)&&(b instanceof  ArrayList)) {
            if (((ArrayList) a).size() == ((ArrayList) b).size()) {
                for (int j = 0; j < ((ArrayList) a).size(); j++) {
                    result.add(new ArrayList<Object>());
                    calculate((ArrayList<Object>)result.get(j),((ArrayList) a).get(j), ((ArrayList) b).get(j), symbol);
                }
            }
            else{
                throw new CannotPossiblyCalculateException("Different matrix size");
            }
        }
        else if((a instanceof Integer)&&(b instanceof  Integer)){
            switch (symbol){
                case "+":
                    result.add(((int)a+(int)b));
                    break;
                case "-":
                    result.add((int)a-(int)b);
            }
        }
        return result;
    }

    private Matrix calculatePlusOrMinus(Matrix a, Matrix b, String symbol) throws CannotPossiblyCalculateException {
        Matrix result = null;
        if(a.length()==b.length()){
            ArrayList<Object> res = new ArrayList<>();
            ArrayList<Object> matrA = a.getMatrix();
            ArrayList<Object> matrB = b.getMatrix();
            for (int i=0;i<matrA.size();i++){
                if(matrA.get(i) instanceof ArrayList) {
                    res.add(new ArrayList<Object>());
                    calculate((ArrayList)res.get(i),matrA.get(i), matrB.get(i), symbol);
                }else{
                    res.add((Integer)res.get(i)+(Integer)matrB.get(i));
                }
            }
            result = new Matrix(res);
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
