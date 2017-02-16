package Model.LockStatements;

import Exceptions.KeyNotFoundMapException;
import Model.ProgramState;
import Model.Statements.IStatement;

/**
 * Created by bnorbert on 25.01.2017.
 * bnorbertcristian@gmail.com
 */
public class LockStatement implements IStatement {

    String var;

    public LockStatement(){}
    public LockStatement(String var){
        this.var=var;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws KeyNotFoundMapException {

        Integer lockKey=programState.getSymbolTable().lookup(var);
        synchronized (programState.getLockTable()){
            Integer lockValue=programState.getLockTable().lookup(lockKey);
            if(lockValue == -1){
                programState.getLockTable().synchronizedUnion(lockKey,programState.getProgramId());
            }
            else {
                programState.getExecutionStack().push(this);
            }
        }

        return null;
    }

    @Override
    public String toString(){
        return "lock(" + var + ")";
    }


}
