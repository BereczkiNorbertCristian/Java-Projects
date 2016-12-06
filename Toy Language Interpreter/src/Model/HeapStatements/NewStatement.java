package Model.HeapStatements;

import Collections.Heap;
import Collections.IToyMap;
import Exceptions.NewHeapException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;

import java.io.Serializable;

/**
 * Created by bnorbert on 24.11.2016.
 * bnorbertcristian@gmail.com
 */
public class NewStatement implements IStatement,Serializable {

    String varName;
    Expression expression;

    public NewStatement(){}

    public NewStatement(String varName, Expression expression){
        this.varName=varName;
        this.expression=expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws NewHeapException{

        Heap heap=programState.getHeap();
        IToyMap<String,Integer> symbolTable=programState.getSymbolTable();
        try {
            int address = heap.put(expression.eval(symbolTable,heap));
            symbolTable.put(varName,address);
        }
        catch (Exception e){
            throw new NewHeapException("Problem in heap allcation: "+e.getMessage());
        }

        return null;
    }

    @Override
    public String toString(){

        return varName+"="+"new("+expression.toString()+");";
    }

}
