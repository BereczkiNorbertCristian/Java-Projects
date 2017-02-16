package Model.LockStatements;

import Collections.ILockTable;
import Model.ProgramState;
import Model.Statements.IStatement;

/**
 * Created by bnorbert on 25.01.2017.
 * bnorbertcristian@gmail.com
 */
public class NewLockStatement implements IStatement {

    String var;

    public NewLockStatement(){}
    public NewLockStatement(String var){
        this.var=var;
    }

    @Override
    public ProgramState execute(ProgramState programState){

        Integer newLocation=programState.getLockTable().getFreeLocation();
        ILockTable lockTableL=programState.getLockTable();
        synchronized (lockTableL){

            lockTableL.synchronizedUnion(newLocation,-1);
        }
        programState.getSymbolTable().put(var,newLocation);
        return null;
    }

    @Override
    public String toString(){
        return "newLock(" + var + ")";
    }



}
