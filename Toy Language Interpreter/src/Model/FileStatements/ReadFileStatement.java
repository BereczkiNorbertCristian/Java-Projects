package Model.FileStatements;

import Exceptions.FileNotExistsException;
import Exceptions.VariableNotDefinedException;
import Model.Expressions.Expression;
import Model.Statements.IStatement;
import Model.Pair;
import Model.ProgramState;

import java.io.BufferedReader;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ReadFileStatement implements IStatement {

    Expression variableFileId;
    String variableName;

    public ReadFileStatement(Expression variableFileId,String variableName){
        this.variableFileId=variableFileId;
        this.variableName=variableName;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception{

        Pair<String, BufferedReader> filenameBufferPair;
        try {
            filenameBufferPair = programState.getFileTable().lookup(variableFileId.eval(programState.getSymbolTable()));
        }
        catch (VariableNotDefinedException e){
            throw new FileNotExistsException("The file you tried to read from does not exit");
        }

        String read=filenameBufferPair.getSecond().readLine();

        int readValue;

        if(read == null){
            readValue=0;
        }
        else{
            readValue=Integer.parseInt(read);
        }
        programState.getSymbolTable().put(variableName,readValue);

        return programState;
    }

    @Override
    public String toString(){

        return variableName + "=readFromFile("+variableFileId.toString()+")";
    }

}
