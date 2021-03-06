package ModelTest;

import Collections.*;
import Model.Statements.AssignmentStatement;
import Model.Expressions.ConstantExpression;
import Model.Statements.IStatement;
import Model.ProgramState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 03.11.2016.
 * bnorbertcristian@gmail.com
 */
public class AssignmentStatementTest {

    ProgramState programState;
    IStatement assignmentStatement;

    @Before
    public void setUp() {

        programState = new ProgramState(new ToyStack<IStatement>(), new ToyMap<String, Integer>(), new ToyList<String>(),new FileTable());

        assignmentStatement = new AssignmentStatement("res", new ConstantExpression(4));

    }

    @Test
    public void executeTest() {

        try {
            assignmentStatement.execute(programState);
            Assert.assertEquals(4, programState.getSymbolTable().lookup("res").intValue());
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void toStringTest() {

        Assert.assertEquals("res=4", assignmentStatement.toString());

    }
}
