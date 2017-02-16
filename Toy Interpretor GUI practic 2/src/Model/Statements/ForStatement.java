package Model.Statements;

import Model.Expressions.Expression;
import Model.Expressions.IntegerComparissonExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState;

/**
 * Created by bnorbert on 24.01.2017.
 * bnorbertcristian@gmail.com
 */
public class ForStatement implements IStatement {

    private String var;
    private Expression exp1,exp2,exp3;
    private IStatement forBody;

    public ForStatement(){}
    public ForStatement(String var,Expression exp1,Expression exp2,Expression exp3,IStatement forBody){
        this.var=var;
        this.exp1=exp1;
        this.exp2=exp2;
        this.exp3=exp3;
        this.forBody=forBody;
    }

    @Override
    public ProgramState execute(ProgramState programState){

        IStatement pushing=new CompoundStatement(
                new AssignmentStatement(var,exp1),
                        new WhileStatement(
                                new IntegerComparissonExpression(new VariableExpression(var),exp2,"<"),
                                new CompoundStatement(forBody,new AssignmentStatement(var,exp3)))
        );

        programState.getExecutionStack().push(pushing);

        return null;
    }


    @Override
    public String toString(){

        return "for(" + var + "=" + exp1.toString() + ";" + var +"<" + exp2.toString() + ";" + var + "=" + exp3.toString() + ")\n" + forBody.toString();
    }

}
