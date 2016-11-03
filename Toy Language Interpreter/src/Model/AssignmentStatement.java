package Model;

import Collections.IToyMap;

/**
 * Created by bnorbert on 22.10.2016.
 */
public class AssignmentStatement implements IStatement {

    String variableId;
    Expression expression;

    public AssignmentStatement(String variableId, Expression expression) {
        this.expression = expression;
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        IToyMap<String, Integer> symbolTable = state.getSymbolTable();

        int value = expression.eval(symbolTable);

        symbolTable.put(variableId, value);

        return state;
    }

    @Override
    public String toString() {

        return variableId + "=" + expression.toString();

    }

}
