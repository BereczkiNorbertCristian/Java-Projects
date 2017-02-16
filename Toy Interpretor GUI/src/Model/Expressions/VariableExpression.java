package Model.Expressions;

import Collections.Heap;
import Collections.IHeap;
import Collections.IToyMap;
import Exceptions.KeyNotFoundMapException;
import Exceptions.VariableNotDefinedException;

import java.io.Serializable;

/**
 * Created by bnorbert on 22.10.2016.
 */
public class VariableExpression extends Expression implements Serializable {

    String variableId;

    public VariableExpression(){}

    public VariableExpression(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public int eval(IToyMap<String, Integer> symbolTable, IHeap<Integer> heap) throws VariableNotDefinedException {

        Integer lookedUp = null;
        try {
            lookedUp = symbolTable.lookup(variableId);
        } catch (KeyNotFoundMapException e) {
            throw new VariableNotDefinedException("Variable not defined");
        }
        return lookedUp.intValue();
    }

    @Override
    public String toString() {
        return variableId;
    }

}
