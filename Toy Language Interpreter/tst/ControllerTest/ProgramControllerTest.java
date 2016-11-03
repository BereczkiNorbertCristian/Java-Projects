package ControllerTest;

import Collections.*;
import Controller.ProgramController;
import Model.*;
import Repository.StateRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 03.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ProgramControllerTest {

    ProgramController ctrl;

    @Before
    public void setUp() {

        ctrl = new ProgramController(new StateRepository());

        IToyStack<IStatement> exeStack = new ToyStack<>();
        IStatement program = new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new ConstantExpression(5), new ArithmeticExpression(
                new ConstantExpression(7), new ConstantExpression(2), 3), 1)), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(
                new VariableExpression("a"), new ArithmeticExpression(new ConstantExpression(33), new ConstantExpression(2), 4), 1)),
                new PrintStatement(new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), 1))));

        exeStack.push(program);

        ProgramState programState = new ProgramState(exeStack, new ToyMap<String, Integer>(), new ToyList<String>());

        ctrl.addProgram(programState);
    }

    @Test
    public void oneStepTest() {

        try {
            ctrl.oneStepCurrentProgram();
            Assert.assertEquals(2, ctrl.getCurrentProgram().getExecutionStack().size());
            ctrl.oneStepCurrentProgram();
            Assert.assertEquals(1, ctrl.getCurrentProgram().getExecutionStack().size());
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void allStepTest() {

        try {
            ctrl.allStep();
            Assert.assertTrue(false);
        } catch (Exception e) {
            //The execution stack is empty
            Assert.assertEquals(0, ctrl.getCurrentProgram().getExecutionStack().size());
            try {
                Assert.assertEquals(19, ctrl.getCurrentProgram().getSymbolTable().lookup("a").intValue());
            } catch (Exception ex) {
                Assert.assertTrue(false);
            }
        }
    }

    @Test
    public void getCurrentOutputTest(){

        try{
            ctrl.allStep();
        }
        catch (Exception e){
            Assert.assertEquals("54 ",ctrl.getCurrentProgram().getOut().get(0));
        }
    }   

}
