package Model.Statements;

import Collections.IToyList;
import Model.Expressions.Expression;
import Model.ProgramState;

import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Created by bnorbert on 22.10.2016.
 */
public class PrintStatement implements IStatement,Serializable {

    Expression expression;

    public PrintStatement(){}

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {

        IToyList<String> out = programState.getOut();
        Integer ret = expression.eval(programState.getSymbolTable(),programState.getHeap());
        out.add(ret.toString() + " ");

        return programState;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

}
