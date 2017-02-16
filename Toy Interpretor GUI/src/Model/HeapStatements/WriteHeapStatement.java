package Model.HeapStatements;

import Collections.Heap;
import Collections.IHeap;
import Collections.IToyMap;
import Exceptions.DivisionByZeroException;
import Exceptions.KeyNotFoundMapException;
import Exceptions.VariableNotDefinedException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;
import Repository.StateRepository;

import java.io.Serializable;
import java.sql.Statement;

/**
 * Created by bnorbert on 25.11.2016.
 * bnorbertcristian@gmail.com
 */
public class WriteHeapStatement implements IStatement,Serializable {

    String varName;
    Expression expression;

    public WriteHeapStatement(){}

    public  WriteHeapStatement(String varName,Expression expression){
        this.varName=varName;
        this.expression=expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws VariableNotDefinedException,DivisionByZeroException{

        IHeap<Integer> heap=programState.getHeap();
        IToyMap<String,Integer> symbolTable=programState.getSymbolTable();
        Integer address;

        try{
            address=symbolTable.lookup(varName);
        }
        catch (KeyNotFoundMapException e){
            throw new VariableNotDefinedException("In Symbol Table: did not found "+varName+"\n"+e.getMessage());
        }

        try {
            heap.updateAtAddress(address, expression.eval(symbolTable, heap));
        }
        catch (DivisionByZeroException e){
            throw new DivisionByZeroException(e.getMessage());
        }


        return null;
    }

    @Override
    public String toString(){
        return "WriteToHeap("+varName+","+expression.toString()+")";
    }

}
