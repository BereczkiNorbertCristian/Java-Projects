package ModelTest.HeapTest;

import Collections.FileTable;
import Collections.ToyList;
import Collections.ToyMap;
import Collections.ToyStack;
import Model.Expressions.ConstantExpression;
import Model.HeapStatements.NewStatement;
import Model.ProgramState;
import Model.Statements.IStatement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 06.12.2016.
 * bnorbertcristian@gmail.com
 */
public class NewStatementTest {

    ProgramState programState;
    IStatement newStatement;

    @Before
    public void setUp(){

        programState = new ProgramState(new ToyStack<IStatement>(),
                new ToyMap<String, Integer>(),
                new ToyList<String>(),
                new FileTable());

        newStatement=new NewStatement("v",new ConstantExpression(10));
    }

    @Test
    public void executeTest(){

        try{
            newStatement.execute(programState);
            Assert.assertEquals(10,programState.getHeap().get(programState.getSymbolTable().lookup("v")));
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }
    }

}
