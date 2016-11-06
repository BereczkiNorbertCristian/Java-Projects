package Model.FileStatements;

import Exceptions.FileNotExistsException;
import Exceptions.VariableNotDefinedException;
import Model.Expressions.Expression;
import Model.Statements.IStatement;
import Model.ProgramState;

import java.io.BufferedReader;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class CloseFileStatement implements IStatement {

    Expression variableFileId;

    public CloseFileStatement(Expression variableFileId){
        this.variableFileId=variableFileId;
    }

    public ProgramState execute(ProgramState programState) throws Exception{

        BufferedReader bufferedReader;
        int fileDescriptor;
        try{
            fileDescriptor=variableFileId.eval(programState.getSymbolTable());
            bufferedReader=programState.getFileTable().lookup(fileDescriptor).getSecond();
        }catch (VariableNotDefinedException e){
            throw new FileNotExistsException("File does not exist");
        }

        bufferedReader.close();
        programState.getFileTable().remove(fileDescriptor);

        return programState;
    }

}
