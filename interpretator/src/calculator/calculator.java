package calculator;

import model.Matrix;

import java.util.ArrayList;

/**
 * Created by андрей on 04.11.2016.
 */
public class calculator {
    
    public Matrix plus(Matrix a, Matrix b){
       return calculatePlusOrMinus(a,b,"+");
    }

    public Matrix minus(Matrix a, Matrix b){
       return calculatePlusOrMinus(a,b,"-");
    }

    public Matrix multiply (Matrix a, Matrix b){
        Matrix result= null;
        return result;
    }
    private void calculate(Object a, Object b, String symbol){
        if((a instanceof ArrayList)&&(b instanceof  ArrayList)) {
            if (((ArrayList) a).size() == ((ArrayList) b).size()) {
                for (int j = 0; j < ((ArrayList) a).size(); j++) {
                    calculate(((ArrayList) a).get(j), ((ArrayList) b).get(j), symbol);
                }
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

    private Matrix calculatePlusOrMinus(Matrix a, Matrix b, String symbol){
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
        }
        return result;
    }
}
