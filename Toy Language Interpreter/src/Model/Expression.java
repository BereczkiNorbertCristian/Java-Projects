package Model;

import Collections.IToyMap;
import Exceptions.DivisionByZeroException;
import Exceptions.VariableNotDefinedException;

/**
 * Created by bnorbert on 21.10.2016.
 */
public abstract class Expression {

    abstract public int eval(IToyMap<String, Integer> symbolTable) throws VariableNotDefinedException, DivisionByZeroException;

}
