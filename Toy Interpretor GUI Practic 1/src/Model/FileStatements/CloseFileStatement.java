package Model.FileStatements;

import Exceptions.FileNotExistsException;
import Exceptions.VariableNotDefinedException;
import Model.Expressions.Expression;
import Model.Statements.IStatement;
import Model.ProgramState;

import java.io.BufferedReader;
import java.io.Serializable;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class CloseFileStatement implements IStatement,Serializable {

    Expression variableFileId;

    public CloseFileStatement(){}

    public CloseFileStatement(Expression variableFileId){
        this.variableFileId=variableFileId;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception{

        BufferedReader bufferedReader;
        int fileDescriptor;
        try{
            fileDescriptor=variableFileId.eval(programState.getSymbolTable(),programState.getHeap());
            bufferedReader=programState.getFileTable().lookup(fileDescriptor).getSecond();
        }catch (VariableNotDefinedException e){
            throw new FileNotExistsException("File does not exist");
        }

        bufferedReader.close();
        programState.getFileTable().remove(fileDescriptor);

        return null;
    }

    @Override
    public String toString(){

        return "close("+variableFileId.toString()+");";
    }

}
