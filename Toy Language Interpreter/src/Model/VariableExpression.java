package Model;

import Collections.IToyMap;
import Exceptions.KeyNotFoundMapException;
import Exceptions.VariableNotDefinedException;

/**
 * Created by bnorbert on 22.10.2016.
 */
public class VariableExpression extends Expression {

    String variableId;

    public VariableExpression(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public int eval(IToyMap<String, Integer> symbolTable) throws VariableNotDefinedException {

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
