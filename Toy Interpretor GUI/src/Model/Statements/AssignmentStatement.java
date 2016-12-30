package Model.Statements;

import Collections.IToyMap;
import Collections.UniqueTrie;
import Model.Expressions.Expression;
import Model.ProgramState;

import java.io.Serializable;

/**
 * Created by bnorbert on 22.10.2016.
 */
public class AssignmentStatement implements IStatement,Serializable {

    String variableId;
    Expression expression;

    public AssignmentStatement(){}

    public AssignmentStatement(String variableId, Expression expression) {
        this.expression = expression;
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        IToyMap<String, Integer> symbolTable = state.getSymbolTable();

        int value = expression.eval(symbolTable,state.getHeap());



        symbolTable.put(variableId, value);

        UniqueTrie uniqueNumberSet=state.getUniqueNumbersSet();
        uniqueNumberSet.insertValue(value);

        return null;
    }

    @Override
    public String toString() {

        return variableId + "=" + expression.toString();

    }

}
