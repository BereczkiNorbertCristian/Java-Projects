package Model;

import Collections.*;
import Exceptions.SerializationException;
import Model.Statements.IStatement;

import javax.swing.*;
import java.io.*;

/**
 * Created by bnorbert on 21.10.2016.
 */
public class ProgramState implements Serializable {

    private IToyStack<IStatement> executionStack;
    private IToyMap<String, Integer> symbolTable;
    private IToyList<String> out;
    private UniqueTrie uniqueNumbersSet;
    private IToyMap<Integer,Pair<String,BufferedReader>> fileTable;
    private Heap<Integer> heap;
    private transient final String fileSerializableName="test.ser";
    private final int programId;
    private ILockTable lockTable;

    public int getProgramId() {
        return programId;
    }


    public void setState(ProgramState other){

        if(other instanceof ProgramState){

            this.heap=other.getHeap();
            this.executionStack=other.getExecutionStack();
            this.symbolTable=other.getSymbolTable();
            this.out=other.getOut();
            this.uniqueNumbersSet=other.getUniqueNumbersSet();
            this.fileTable=other.getFileTable();

        }

    }


    public IToyMap<Integer, Pair<String, BufferedReader>> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IToyMap<Integer, Pair<String, BufferedReader>> fileTable) {
        this.fileTable = fileTable;
    }

    public void setLockTable(ILockTable lockTable){
        this.lockTable=lockTable;
    }
    public ILockTable getLockTable(){
        return lockTable;
    }


    public ProgramState(IToyStack<IStatement> executionStack,
                        IToyMap<String, Integer> symbolTable,
                        IToyList<String> out,
                        IToyMap<Integer,Pair<String,BufferedReader>> fileTable,
                        int programId) {

        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.uniqueNumbersSet=new UniqueTrie(17);
        this.fileTable=fileTable;
        this.heap=new Heap<>();
        this.programId=programId;
    }

    public void setUniqueNumbersSet(UniqueTrie uniqueNumbersSet){
        this.uniqueNumbersSet=uniqueNumbersSet;
    }

    public UniqueTrie getUniqueNumbersSet(){
        return uniqueNumbersSet;
    }

    public IToyStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(IToyStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }


    public IToyMap<String, Integer> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(IToyMap<String, Integer> symbolTable) {
        this.symbolTable = symbolTable;
    }


    public IToyList<String> getOut() {
        return out;
    }

    public void setOut(IToyList<String> out) {
        this.out = out;
    }

    public Heap getHeap(){
        return this.heap;
    }

    public void setHeap(Heap<Integer> heap){
        this.heap=heap;
    }

    @Override
    public String toString() {

        String outString = "";

        outString += "PROGRAM ID:"+Integer.toString(programId)+"\n"+
                "THE EXECUTION STACK:\n" + executionStack.toString() +
                "THE SYMBOL TABLE:\n" + symbolTable.toString() +
                "THE OUTPUT LIST:\n" + out.toString() +
                "THE FILETABLE:\n" + fileTable.toString() +
                "HEAP:\n" + heap.toString() +
                "\n"
        ;

        return outString;
    }

    public void serialize() throws SerializationException{

        try{
            FileOutputStream fileOut=
                    new FileOutputStream(this.fileSerializableName);
            ObjectOutputStream  out= new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        }
        catch (IOException ioe){
            throw new SerializationException("Serialization Error:"+ioe.getMessage());
        }
    }

    public ProgramState obtainSerializable() throws SerializationException{

        try{

            FileInputStream fileIn = new FileInputStream(fileSerializableName);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            ProgramState programState;
            programState=(ProgramState) in.readObject();
            in.close();
            fileIn.close();

            return programState;
        }
        catch (IOException ioe){
            throw new SerializationException("Error at Deserialising:"+ioe.getMessage());
        }
        catch (ClassNotFoundException cnfe){
            throw new SerializationException("Error at Deserialising"+cnfe.getMessage());
        }

    }

    public boolean isNotCompleted(){
        return !executionStack.empty();
    }

    public ProgramState oneStep() throws Exception{

        if(executionStack.empty()){
            //throw new Exception("Program finished!");
            return this;
        }
        IStatement currentStatement=executionStack.pop();
        return currentStatement.execute(this);
    }


}
