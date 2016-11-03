package ModelTest;

import Collections.ToyList;
import Collections.ToyMap;
import Collections.ToyStack;
import Model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 03.11.2016.
 * bnorbertcristian@gmail.com
 */
public class IfStatementTest {

    ProgramState programState;
    IStatement ifStatement1;
    IStatement ifStatement2;

    @Before
    public void setUp(){

        programState=new ProgramState(new ToyStack<IStatement>(),new ToyMap<String, Integer>(),new ToyList<String>());

        ifStatement1=new IfStatement(
                new ConstantExpression(0),
                new PrintStatement(new ConstantExpression(5)),
                new PrintStatement(new ConstantExpression(3))
        );

        ifStatement2=new IfStatement(
                new ConstantExpression(21),
                new PrintStatement(new ConstantExpression(9)),
                new PrintStatement(new ConstantExpression(8))
        );

    }

    @Test
    public void executeTest(){

        try {
            ifStatement1.execute(programState);
            Assert.assertEquals("5",programState.getOut().get(0).trim());
            ifStatement2.execute(programState);
            Assert.assertEquals("8",programState.getOut().get(1).trim());
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void toStringTest(){

        Assert.assertEquals("IF(0) THEN {print(5)} ELSE {print(3)}",ifStatement1.toString());

    }

}
