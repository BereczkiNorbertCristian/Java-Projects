package Model.Expressions;

import Collections.Heap;
import Collections.IHeap;
import Collections.IToyMap;
import Exceptions.VariableNotDefinedException;

import java.io.Serializable;

/**
 * Created by bnorbert on 22.10.2016.
 */
public class ConstantExpression extends Expression implements Serializable {

    int number;

    public ConstantExpression(){}

    public ConstantExpression(int number) {
        this.number = number;
    }

    @Override
    public int eval(IToyMap<String, Integer> symbolTable, IHeap<Integer> heap) throws VariableNotDefinedException {
        return number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
