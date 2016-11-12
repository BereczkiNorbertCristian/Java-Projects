package ModelTest.FileStatements;

import Collections.FileTable;
import Collections.ToyList;
import Collections.ToyMap;
import Collections.ToyStack;
import Model.Expressions.VariableExpression;
import Model.FileStatements.OpenFileStatement;
import Model.FileStatements.ReadFileStatement;
import Model.ProgramState;
import Model.Statements.IStatement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 12.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ReadFileTest {

    ProgramState programState;
    IStatement readFileStatement;

    @Before
    public void setUp(){

        programState = new ProgramState(
                new ToyStack<IStatement>(),
                new ToyMap<String,Integer>(),
                new ToyList<String>(),
                new FileTable());

        IStatement openFileStatement = new OpenFileStatement("fin","test.in");

        try {
            openFileStatement.execute(programState);
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }

        readFileStatement=new ReadFileStatement(new VariableExpression("fin"),"var_c");

    }

    @Test
    public void executeTest(){


        try {
            readFileStatement.execute(programState);
            Assert.assertEquals(15,programState.getSymbolTable().lookup("var_c").intValue());
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }
    }
}
