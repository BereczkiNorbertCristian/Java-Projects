package RepositoryTest;

import Collections.*;
import Model.Statements.IStatement;
import Model.ProgramState;
import Repository.IStateRepository;
import Repository.StateRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bnorbert on 03.11.2016.
 * bnorbertcristian@gmail.com
 */
public class StateRepositoryTest {

    ProgramState programState;
    IStateRepository repo;

    @Before
    public void setUp() {

        programState = new ProgramState(new ToyStack<IStatement>(), new ToyMap<String, Integer>(), new ToyList<String>(),new FileTable());
        try {
            repo = new StateRepository();
        }
        catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void allMethodsTest() {

        repo.addProgram(programState);

        IToyMap<String, Integer> symTable = new ToyMap<>();

        symTable.put("res", 3);

        ProgramState state1 = new ProgramState(new ToyStack<IStatement>(), symTable, new ToyList<String>(),new FileTable());

        repo.updateProgram(state1);

        repo.setCurrent(0);

        try {
            Assert.assertEquals(3, repo.getCurrentProgram().getSymbolTable().lookup("res").intValue());
        } catch (Exception e) {
            Assert.assertTrue(false);
        }

    }

}
