package Model.Statements;

import Model.ProgramState;

/**
 * Created by bnorbert on 24.01.2017.
 * bnorbertcristian@gmail.com
 */
public class Skip implements IStatement {

    @Override
    public ProgramState execute(ProgramState programState){
        return null;
    }

    @Override
    public String toString(){
        return "skip";
    }

}
