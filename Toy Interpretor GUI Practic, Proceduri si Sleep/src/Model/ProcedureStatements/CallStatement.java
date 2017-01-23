package Model.ProcedureStatements;

import Collections.IToyMap;
import Collections.ToyMap;
import Model.Expressions.Expression;
import Model.ProcMapping;
import Model.ProgramState;
import Model.Statements.IStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bnorbert on 23.01.2017.
 * bnorbertcristian@gmail.com
 */
public class CallStatement implements IStatement {

    private List<Expression> parameters;
    private String procedure;

    public CallStatement(){}
    public CallStatement(String procedure,List<Expression> parameters){
        this.parameters=parameters;
        this.procedure=procedure;
    }

    @Override
    public ProgramState execute(ProgramState programState)throws Exception{

        ProcMapping procMapping=programState.getProcTable().lookup(procedure);

        List<Integer> argsValues=evaluateArgs(programState);

        IToyMap<String,Integer> newSymTable = createNewSymTable(procMapping.getFormalParams(),argsValues);

        programState.setSymbolTable(newSymTable);

        programState.getAllSymTables().push(newSymTable);
        programState.getExecutionStack().push(new ReturnStatement());
        programState.getExecutionStack().push(procMapping.getProcBody());

        return null;
    }

    private List<Integer> evaluateArgs(ProgramState programState)throws Exception{
        List<Integer> ret=new ArrayList<>();
        for(Expression expression : parameters){
            ret.add(expression.eval(programState.getSymbolTable(),programState.getHeap()));
        }
        return ret;
    }

    private IToyMap<String,Integer> createNewSymTable(List<String> arguments,List<Integer> argsValues){

        IToyMap<String,Integer> newSymT=new ToyMap<String, Integer>();

        for(int i=0;i<arguments.size();i++){
            newSymT.put(arguments.get(i),argsValues.get(i));
        }
        return newSymT;
    }

    @Override
    public String toString(){
        String ans="";
        for(Expression expression : parameters){
            ans+=expression.toString() + ",";
        }
        return "call " + procedure + "(" + ans + ")";
    }

}
