package ModelTest;

import Collections.FileTable;
import Collections.ToyList;
import Collections.ToyMap;
import Collections.ToyStack;
import Model.*;
import Model.Expressions.ConstantExpression;
import Model.Statements.AssignmentStatement;
import Model.Statements.CompoundStatement;
import Model.Statements.IStatement;
import Model.Statements.PrintStatement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 03.11.2016.
 * bnorbertcristian@gmail.com
 */
public class CompoundStatementTest {

    ProgramState programState;
    IStatement compoundStatement;

    @Before
    public void setUp() {

        programState = new ProgramState(new ToyStack<IStatement>(), new ToyMap<String, Integer>(), new ToyList<String>(),new FileTable());

        compoundStatement = new CompoundStatement(
                new PrintStatement(new ConstantExpression(8)),
                new AssignmentStatement("ans", new ConstantExpression(4)));

    }

    @Test
    public void executeTest() {

        try {
            compoundStatement.execute(programState);
            Assert.assertEquals(2, programState.getExecutionStack().size());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.assertTrue(false);
        }

    }

    @Test
    public void toStringTest() {

        Assert.assertEquals(15, compoundStatement.toString().length());

    }

}
