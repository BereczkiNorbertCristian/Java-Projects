package Model.Expressions;

import Collections.Heap;
import Collections.IToyMap;
import Exceptions.DivisionByZeroException;
import Exceptions.VariableNotDefinedException;

import java.io.Serializable;

/**
 * Created by bnorbert on 21.10.2016.
 */
public abstract class Expression implements Serializable {

    public Expression(){}

    abstract public int eval(IToyMap<String, Integer> symbolTable,Heap<Integer> heap) throws VariableNotDefinedException, DivisionByZeroException;

}
