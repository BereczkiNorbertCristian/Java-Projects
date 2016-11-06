package Model;

import Collections.IToyList;
import Collections.IToyMap;
import Collections.IToyStack;
import Collections.UniqueTrie;
import Model.Statements.IStatement;

import java.io.BufferedReader;

/**
 * Created by bnorbert on 21.10.2016.
 */
public class ProgramState {

    private IToyStack<IStatement> executionStack;
    private IToyMap<String, Integer> symbolTable;
    private IToyList<String> out;
    private UniqueTrie uniqueNumbersSet;
    private IToyMap<Integer,Pair<String,BufferedReader>> fileTable;

    public IToyMap<Integer, Pair<String, BufferedReader>> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IToyMap<Integer, Pair<String, BufferedReader>> fileTable) {
        this.fileTable = fileTable;
    }




    public ProgramState(IToyStack<IStatement> executionStack,
                        IToyMap<String, Integer> symbolTable,
                        IToyList<String> out,
                        IToyMap<Integer,Pair<String,BufferedReader>> fileTable) {

        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.uniqueNumbersSet=new UniqueTrie(17);
        this.fileTable=fileTable;
    }

    public void setUniqueNumbersSet(UniqueTrie uniqueNumbersSet){
        this.uniqueNumbersSet=uniqueNumbersSet;
    }

    public UniqueTrie getUniqueNumbersSet(){
        return uniqueNumbersSet;
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
