package Model.Expressions;

import Collections.IToyMap;
import Exceptions.DivisionByZeroException;
import Exceptions.VariableNotDefinedException;

/**
 * Created by bnorbert on 21.10.2016.
 */
public class ArithmeticExpression extends Expression {

    Expression expression1;
    Expression expression2;
    int operation; // 1->+, 2->- 3->* 4->/

    public ArithmeticExpression(Expression expression1, Expression expression2, int operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    public int eval(IToyMap<String, Integer> symbolTable) throws VariableNotDefinedException, DivisionByZeroException {

        switch (operation) {

            case 1:
                return expression1.eval(symbolTable) + expression2.eval(symbolTable);
            case 2:
                return expression1.eval(symbolTable) - expression2.eval(symbolTable);
            case 3:
                return expression1.eval(symbolTable) * expression2.eval(symbolTable);
            default:
                int retExp2 = expression2.eval(symbolTable);
                if (retExp2 == 0) {
                    throw new DivisionByZeroException("Second expression is 0");
                }
                return expression1.eval(symbolTable) / retExp2;
        }
    }

    @Override
    public String toString() {

        String sign;

        if (operation == 1) {
            sign = "+";
        } else {
            sign = "-";
        }
        return expression1.toString() + sign + expression2.toString();
    }

}
