package ModelTest;

import Collections.*;
import Exceptions.SerializationException;
import Model.Expressions.ConstantExpression;
import Model.ProgramState;
import Model.Statements.AssignmentStatement;
import Model.Statements.IStatement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 02.12.2016.
 * bnorbertcristian@gmail.com
 *
 * Teste the serialization and deserialization of the program state
 */
public class SerializationTest {

    ProgramState state;

    @Before
    public void setUp(){

        IToyList<String> outList = new ToyList<String>();
        IToyMap<String, Integer> symbolTable = new ToyMap<String, Integer>();
        IToyStack<IStatement> exeStack = new ToyStack<IStatement>();

        symbolTable.put("a",3);
        outList.add("3").add("7").add("9");
        exeStack.push(new AssignmentStatement("b",new ConstantExpression(18)));

        state = new ProgramState(exeStack, symbolTable, outList,new FileTable());
    }

    @Test
    public void serializationTest(){

        try {
            state.serialize();
        }
        catch (SerializationException se){
            Assert.assertTrue(false);
        }

        try{
            Assert.assertEquals(3,state.obtainSerializable().getSymbolTable().lookup("a").intValue());
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }
        try{
            ProgramState prg=state.obtainSerializable();
            Assert.assertEquals("b=18",prg.getExecutionStack().peek().toString());
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }
    }

}
