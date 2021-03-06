package Model.Statements;

import Collections.IToyMap;
import Model.Expressions.Expression;
import Model.ProgramState;

import java.io.Serializable;

/**
 * Created by bnorbert on 22.10.2016.
 * bnorbertcristian@gmail.com
 */
public class IfStatement implements IStatement,Serializable {

    Expression expression;
    IStatement thenStatement;
    IStatement elseStatement;

    public IfStatement(){}

    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        IToyMap<String, Integer> symbolTable = state.getSymbolTable();

        int value = expression.eval(symbolTable,state.getHeap());

        ProgramState resultedState;

        if (value == 0) {
            resultedState = thenStatement.execute(state);
        } else {
            resultedState = elseStatement.execute(state);
        }
        return null;
    }

    @Override
    public String toString() {

        return "IF(" + expression.toString() + ") THEN {" + thenStatement.toString() + "} ELSE {" + elseStatement.toString() + "}";
    }

}
