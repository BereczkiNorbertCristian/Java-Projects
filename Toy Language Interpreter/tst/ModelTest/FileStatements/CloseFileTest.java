package ModelTest.FileStatements;

import Collections.FileTable;
import Collections.ToyList;
import Collections.ToyMap;
import Collections.ToyStack;
import Controller.ProgramController;
import Model.Expressions.VariableExpression;
import Model.FileStatements.CloseFileStatement;
import Model.FileStatements.OpenFileStatement;
import Model.ProgramState;
import Model.Statements.IStatement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 12.11.2016.
 * bnorbertcristian@gmail.com
 */
public class CloseFileTest {

    ProgramState programState;
    IStatement closeFileStatement;

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

        closeFileStatement = new CloseFileStatement(new VariableExpression("fin"));

    }

    @Test
    public void executeTest(){

        try{
            closeFileStatement.execute(programState);
            try{
                programState.getFileTable().lookup(0);
                Assert.assertTrue(false);
            }
            catch (Exception e){
                Assert.assertTrue(true);
            }

        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

    }

}
