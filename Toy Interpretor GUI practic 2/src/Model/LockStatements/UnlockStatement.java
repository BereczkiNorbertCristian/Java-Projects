package Model.LockStatements;

import Exceptions.KeyNotFoundMapException;
import Model.ProgramState;
import Model.Statements.IStatement;

/**
 * Created by bnorbert on 24.01.2017.
 * bnorbertcristian@gmail.com
 */
public class UnlockStatement implements IStatement {

    String var;

    public UnlockStatement(){}
    public UnlockStatement(String var){
        this.var=var;
    }

    @Override
    public ProgramState execute(ProgramState programState)throws KeyNotFoundMapException{

        Integer foundIndex=programState.getSymbolTable().lookup(var);
        synchronized (programState.getLockTable()){
            Integer lockValue=new Integer(0);
            boolean estiOut=false;
            try {
                lockValue=programState.getLockTable().lookup(foundIndex);
            }
            catch (KeyNotFoundMapException knfe){
                estiOut=true;
            }
            if(!estiOut) {
                if (lockValue == programState.getProgramId()){
                    programState.getLockTable().put(foundIndex,-1);
                }
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return "unlock(" + var + ")";
    }

}
