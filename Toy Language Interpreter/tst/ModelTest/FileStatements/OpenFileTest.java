package ModelTest.FileStatements;

import Collections.FileTable;
import Collections.ToyList;
import Collections.ToyMap;
import Collections.ToyStack;
import Model.FileStatements.OpenFileStatement;
import Model.ProgramState;
import Model.Statements.IStatement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

/**
 * Created by bnorbert on 12.11.2016.
 * bnorbertcristian@gmail.com
 */
public class OpenFileTest {

    ProgramState programState;
    IStatement openFileStatement;

    @Before
    public void setUp(){

        programState = new ProgramState(
                new ToyStack<IStatement>(),
                new ToyMap<String,Integer>(),
                new ToyList<String>(),
                new FileTable());

        try {
            System.setOut(new PrintStream("/dev/null"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        openFileStatement = new OpenFileStatement("fin","test.in");

    }

    @Test
    public void executeTest(){

        try {
            openFileStatement.execute(programState);
            Assert.assertEquals(0,programState.getSymbolTable().lookup("fin").intValue());
            Assert.assertEquals("test.in",programState.getFileTable().lookup(0).getFirst());
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

    }


}
