package Model.Statements;

import Model.Expressions.Expression;
import Model.ProgramState;

import java.io.Serializable;

/**
 * Created by bnorbert on 03.12.2016.
 * bnorbertcristian@gmail.com
 */
public class WhileStatement implements IStatement,Serializable {

    Expression expression;
    IStatement statement;

    public WhileStatement(){}
    public WhileStatement(Expression expression,IStatement statement){
        this.expression=expression;
        this.statement=statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception{

        if(expression.eval(state.getSymbolTable(),state.getHeap()) != 0){

            state.getExecutionStack().push(this);
            state.getExecutionStack().push(statement);
        }

        return null;
    }

    @Override
    public String toString(){
        return "while("+expression.toString()+"){\n"
                +statement.toString()+"\n}";
    }
}
