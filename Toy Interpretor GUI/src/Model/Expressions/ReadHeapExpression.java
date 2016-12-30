package Model.Expressions;

import Collections.Heap;
import Collections.IToyMap;
import Exceptions.IllegalMemoryAccessException;
import Exceptions.KeyNotFoundMapException;
import Exceptions.VariableNotDefinedException;

import java.io.Serializable;

/**
 * Created by bnorbert on 24.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ReadHeapExpression extends Expression implements Serializable {

    String varName;

    public ReadHeapExpression(){}

    public ReadHeapExpression(String varName){
        this.varName=varName;
    }

    @Override
    public int eval(IToyMap<String,Integer> symbolTable, Heap<Integer> heap) throws VariableNotDefinedException{

        Integer address;
        try {
            address = symbolTable.lookup(varName);
        }
        catch (KeyNotFoundMapException e){
            throw new VariableNotDefinedException("In SymbolTable: Variable "+varName+ " not defined in symbol table");
        }

        Integer value;
        try{
            value=heap.get(address.intValue());
        }
        catch (IllegalMemoryAccessException e){
            throw new VariableNotDefinedException("In Heap: "+e.getMessage());
        }


        return value.intValue();
    }

    @Override
    public String toString(){

        return "ReadFromHeap("+varName+")";
    }


}
