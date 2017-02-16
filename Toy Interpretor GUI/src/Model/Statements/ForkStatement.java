package Model.Statements;

import Collections.IToyMap;
import Collections.IToyStack;
import Collections.ToyStack;
import Model.ProgramState;

import java.io.Serializable;

/**
 * Created by bnorbert on 14.12.2016.
 * bnorbertcristian@gmail.com
 */
public class ForkStatement implements IStatement,Serializable {

    IStatement statement;

    public ForkStatement(IStatement statement){
        this.statement=statement;
    }

    @Override
    public ProgramState execute(ProgramState programState){

        IToyStack<IStatement> executionStackForked=new ToyStack<>();
        executionStackForked.push(statement);
        IToyMap<String,Integer> cloned=
                programState.getSymbolTable().clone();



        ProgramState forked=new ProgramState(
                executionStackForked,
                cloned,
                programState.getOut(),
                programState.getFileTable(),
                programState.getProgramId()*10);

        forked.setLockTable(programState.getLockTable());
        forked.setHeap(programState.getHeap());
        forked.setUniqueNumbersSet(programState.getUniqueNumbersSet());

        return forked;
    }

    @Override
    public String toString(){

        return "fork("+statement.toString()+")";
    }

}
