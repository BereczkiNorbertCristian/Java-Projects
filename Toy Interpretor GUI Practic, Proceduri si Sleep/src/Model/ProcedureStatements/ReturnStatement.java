package Model.ProcedureStatements;

import Model.ProgramState;
import Model.Statements.IStatement;

/**
 * Created by bnorbert on 23.01.2017.
 * bnorbertcristian@gmail.com
 */
public class ReturnStatement implements IStatement {

    public ReturnStatement(){}

    @Override
    public ProgramState execute(ProgramState programState){

        programState.getAllSymTables().pop();
        programState.setSymbolTable(programState.getAllSymTables().peek());
        return null;
    }

    @Override
    public String toString(){
        return "return";
    }

}
