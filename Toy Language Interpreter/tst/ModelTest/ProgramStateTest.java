package ModelTest;

import Collections.*;
import Mocks.ToyMapMock;
import Model.Expressions.ConstantExpression;
import Model.Statements.IStatement;
import Model.Statements.PrintStatement;
import Model.ProgramState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 03.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ProgramStateTest {

    ProgramState programState;

    @Before
    public void setUp() {

        IToyList<String> outList = new ToyList<String>();
        IToyMap<String, Integer> symbolTable = new ToyMap<String, Integer>();
        IToyStack<IStatement> exeStack = new ToyStack<IStatement>();

        programState = new ProgramState(exeStack, symbolTable, outList,new FileTable());

    }

    @Test
    public void allMethodsTest() {

        programState.setSymbolTable(new ToyMapMock());

        IToyMap<String, Integer> symTable = programState.getSymbolTable();

        try {
            Assert.assertEquals(3, symTable.lookup("a").intValue());
        } catch (Exception e) {
            Assert.assertTrue(false);
        }

        IToyList<String> out1 = programState.getOut();

        out1.add("23");

        programState.setOut(out1);

        Assert.assertEquals("23", programState.getOut().get(0));

        IToyStack<IStatement> exeStack1 = programState.getExecutionStack();

        exeStack1.push(new PrintStatement(new ConstantExpression(3)));

        programState.setExecutionStack(exeStack1);

        Assert.assertEquals(1, programState.getExecutionStack().size());

    }


}
