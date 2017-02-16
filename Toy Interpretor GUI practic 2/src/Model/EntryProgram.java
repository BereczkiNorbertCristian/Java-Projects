package Model;

import Controller.ProgramController;
import Model.Statements.IStatement;

/**
 * Created by bnorbert on 30.12.2016.
 * bnorbertcristian@gmail.com
 */
public class EntryProgram {

    Integer programIdentifier;
    String initialView;
    ProgramController ctrl;
    ProgramState progam;

    public EntryProgram(){

    }

    public EntryProgram(int programIdentifier, ProgramState prg, ProgramController ctrl){

        this.programIdentifier=new Integer(programIdentifier);
        this.initialView=prg.getExecutionStack().peek().toString();
        this.progam=prg;
        this.ctrl=ctrl;
    }


    public String getInitialView() {
        return programIdentifier.toString() + '\n' + initialView;
    }

    public Integer getProgramIdentifier(){
        return programIdentifier;
    }

    public ProgramController getCtrl(){
        return ctrl;
    }

    public String toString(){
        return getInitialView() + "\n";
    }

    public ProgramState getProgam(){
        return progam;
    }


}
