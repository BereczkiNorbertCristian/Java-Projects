package Model.Statements;

import Model.ProgramState;

/**
 * Created by bnorbert on 14.01.2017.
 * bnorbertcristian@gmail.com
 */
public class EntryIdentifier {

    ProgramState programState;

    public EntryIdentifier(ProgramState programState){
        this.programState=programState;
    }

    public ProgramState getProgram(){
        return programState;
    }

    @Override
    public String toString(){
        return Integer.toString(programState.getProgramId());
    }
}
