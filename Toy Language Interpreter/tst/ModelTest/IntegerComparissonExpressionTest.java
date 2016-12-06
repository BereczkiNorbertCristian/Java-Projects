package ModelTest;

import Collections.FileTable;
import Collections.ToyList;
import Collections.ToyMap;
import Collections.ToyStack;
import Model.Expressions.ConstantExpression;
import Model.Expressions.Expression;
import Model.Expressions.IntegerComparissonExpression;
import Model.ProgramState;
import Model.Statements.IStatement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 06.12.2016.
 * bnorbertcristian@gmail.com
 */
public class IntegerComparissonExpressionTest {

    ProgramState programState;
    Expression booleanExpression;

    @Before
    public void setUp(){

        programState=new ProgramState(new ToyStack<IStatement>(),
                new ToyMap<String, Integer>(),
                new ToyList<String>(),
                new FileTable());

        booleanExpression=new IntegerComparissonExpression(new ConstantExpression(3),new ConstantExpression(5),">");

    }

    @Test
    public void evalTest(){

        try{
            Assert.assertEquals(0,booleanExpression.eval(programState.getSymbolTable(),programState.getHeap()));
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }

    }
}
