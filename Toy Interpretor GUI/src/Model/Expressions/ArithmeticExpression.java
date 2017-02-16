package Model.Expressions;

import Collections.Heap;
import Collections.IHeap;
import Collections.IToyMap;
import Exceptions.DivisionByZeroException;
import Exceptions.VariableNotDefinedException;

import java.io.Serializable;

/**
 * Created by bnorbert on 21.10.2016.
 */
public class ArithmeticExpression extends Expression implements Serializable {

    Expression expression1;
    Expression expression2;
    String operation; // +, - * /

    public ArithmeticExpression(){}

    public ArithmeticExpression(Expression expression1, Expression expression2, String operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public int eval(IToyMap<String, Integer> symbolTable, IHeap<Integer> heap) throws VariableNotDefinedException, DivisionByZeroException {

        switch (operation) {

            case "+":
                return expression1.eval(symbolTable,heap) + expression2.eval(symbolTable,heap);
            case "-":
                return expression1.eval(symbolTable,heap) - expression2.eval(symbolTable,heap);
            case "*":
                return expression1.eval(symbolTable,heap) * expression2.eval(symbolTable,heap);
            default:
                int retExp2 = expression2.eval(symbolTable,heap);
                if (retExp2 == 0) {
                    throw new DivisionByZeroException("Second expression is 0");
                }
                return expression1.eval(symbolTable,heap) / retExp2;
        }
    }

    @Override
    public String toString() {


        return expression1.toString() + operation + expression2.toString();
    }

}
