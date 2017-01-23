package Model.Statements;

import Model.ProgramState;

/**
 * Created by bnorbert on 23.01.2017.
 * bnorbertcristian@gmail.com
 */
public class SleepStatement implements IStatement {

    private int number;

    public SleepStatement(int number){
        this.number=number;
    }

    @Override
    public ProgramState execute(ProgramState program){
        if(number == 0){

        }
        else {
            number--;
            program.getExecutionStack().push(this);
        }
        return null;
    }

    @Override
    public String toString(){
        return "sleep(" + Integer.toString(number) + ")";
    }
}
