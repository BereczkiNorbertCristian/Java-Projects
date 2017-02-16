package Model.Statements;

import Collections.IToyMap;
import Collections.IToyStack;
import Collections.ProcTable;
import Collections.ToyStack;
import Model.ProgramState;

import java.io.Serializable;
import java.util.Stack;

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

        IToyStack<IStatement> executionStackForked=new ToyStack<IStatement>();
        executionStackForked.push(statement);
        IToyMap<String,Integer> cloned=
                programState.getSymbolTable().clone();



        ProgramState forked=new ProgramState(
                executionStackForked,
                cloned,
                programState.getOut(),
                programState.getFileTable(),
                programState.getProgramId()*10);
        forked.setAllSymTables(cloneAllStack(programState.getAllSymTables()));
        forked.setProcTable(programState.getProcTable());

        forked.setHeap(programState.getHeap());
        forked.setUniqueNumbersSet(programState.getUniqueNumbersSet());

        return forked;
    }

    private Stack<IToyMap<String,Integer>> cloneAllStack(Stack<IToyMap<String,Integer>> prgStack){

        Stack<IToyMap<String,Integer>> stk=new Stack<>();
        for(IToyMap<String,Integer> symT : prgStack){
            stk.push(symT.clone());
        }
        return stk;
    }

    @Override
    public String toString(){

        return "fork("+statement.toString()+")";
    }

}
