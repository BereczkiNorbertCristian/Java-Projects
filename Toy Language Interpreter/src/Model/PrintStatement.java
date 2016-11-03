package Model;

import Collections.IToyList;

/**
 * Created by bnorbert on 22.10.2016.
 */
public class PrintStatement implements IStatement {

    Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {

        IToyList<String> out = programState.getOut();
        Integer ret = expression.eval(programState.getSymbolTable());
        out.add(ret.toString() + " ");

        return programState;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

}
