package ModelTest;

import Collections.*;
import Model.ConstantExpression;
import Model.IStatement;
import Model.PrintStatement;
import Model.ProgramState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 03.11.2016.
 * bnorbertcristian@gmail.com
 */
public class PrintStatementTest {

    ProgramState programState;
    IStatement printStatement;

    @Before
    public void setUp(){

        IToyList<String> out=new ToyList<String>();
        IToyMap<String,Integer> symTable=new ToyMap<String,Integer>();
        IToyStack<IStatement> exeStack=new ToyStack<IStatement>();

        printStatement=new PrintStatement(new ConstantExpression(4));

        programState=new ProgramState(exeStack,symTable,out);
    }

    @Test
    public void executeTest(){

        try {
            printStatement.execute(programState);
            Assert.assertEquals("4",programState.getOut().get(0).trim());
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void toStringTest(){

        Assert.assertEquals("print(4)",printStatement.toString());

    }


}
