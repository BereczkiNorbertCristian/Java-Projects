package Model.FileStatements;

import Exceptions.FileExistException;
import Model.Statements.IStatement;
import Model.Pair;
import Model.ProgramState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class OpenFileStatement implements IStatement {

    private String variableFileId;
    private String filename;

    public OpenFileStatement(String variableFileId,String filename){
        this.variableFileId=variableFileId;
        this.filename=filename;
    }

    public ProgramState execute(ProgramState programState) throws FileExistException,IOException{

        if(existsFilenameInFiletable(programState)){
            throw new FileExistException("The file already is opened");
        }
        BufferedReader bufferedReader;
        bufferedReader=new BufferedReader(new FileReader(filename));

        Integer uniqueInteger=programState.getUniqueNumbersSet().uniqueValue();

        programState.getSymbolTable().put(variableFileId,uniqueInteger);
        programState.getFileTable().put(uniqueInteger,new Pair<>(filename,bufferedReader));

        return programState;
    }

    private boolean existsFilenameInFiletable(ProgramState programState){

        for(Pair<String,BufferedReader> entry : programState.getFileTable().values()){
            if(filename == entry.getFirst()){
                return true;
            }
        }
        return false;

    }

    @Override
    public String toString(){

        return this.variableFileId + "=open("+this.filename+")";
    }
}
