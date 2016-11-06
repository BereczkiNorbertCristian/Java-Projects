package Model.Expressions;

import Collections.IToyMap;
import Exceptions.VariableNotDefinedException;

/**
 * Created by bnorbert on 22.10.2016.
 */
public class ConstantExpression extends Expression {

    int number;

    public ConstantExpression(int number) {
        this.number = number;
    }

    @Override
    public int eval(IToyMap<String, Integer> symbolTable) throws VariableNotDefinedException {
        return number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
