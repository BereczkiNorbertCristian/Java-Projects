package Model.Statements;

import Collections.IToyStack;
import Model.ProgramState;
import View.Commands.Command;

import java.io.Serializable;

/**
 * Created by bnorbert on 21.10.2016.
 */
public class CompoundStatement implements IStatement,Serializable {
    IStatement first;
    IStatement second;

    public CompoundStatement(){}

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        IToyStack<IStatement> iToyStack = state.getExecutionStack();

        iToyStack.push(second);
        iToyStack.push(first);

        return state;
    }

    @Override
    public String toString() {


        return first.toString() + ";\n" + second.toString();

    }

}
