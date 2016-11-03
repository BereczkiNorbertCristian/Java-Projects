package Model;

import Collections.IToyList;
import Collections.IToyMap;
import Collections.IToyStack;

/**
 * Created by bnorbert on 21.10.2016.
 */
public class ProgramState {

    IToyStack<IStatement> executionStack;
    IToyMap<String, Integer> symbolTable;
    IToyList<String> out;

    public ProgramState(IToyStack<IStatement> executionStack,
                        IToyMap<String, Integer> symbolTable,
                        IToyList<String> out) {

        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
    }

    public IToyStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(IToyStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }


    public IToyMap<String, Integer> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(IToyMap<String, Integer> symbolTable) {
        this.symbolTable = symbolTable;
    }


    public IToyList<String> getOut() {
        return out;
    }

    public void setOut(IToyList<String> out) {
        this.out = out;
    }

    @Override
    public String toString() {

        String outString = "";

        outString += "THE EXECUTION STACK:\n" + executionStack.toString() +
                "THE SYMBOL TABLE:\n" + symbolTable.toString() +
                "THE OUTPUT LIST:\n" + out.toString()
        ;

        return outString;
    }


}
