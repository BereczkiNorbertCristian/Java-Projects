package Controller;

import Collections.IToyList;
import Exceptions.EmptyStackControllerException;
import Exceptions.ProgramControllerException;
import Model.IStatement;
import Collections.IToyStack;
import Model.ProgramState;
import Repository.IStateRepository;

import java.util.List;

/**
 * Created by bnorbert on 22.10.2016.
 * bnorbertcristian@gmail.com
 */
public class ProgramController {

    IStateRepository statesRepository;

    public ProgramController(IStateRepository statesRepository) {
        this.statesRepository = statesRepository;
    }

    public void addProgram(ProgramState programState) {
        statesRepository.addProgram(programState);
    }

    public void setCurrentProgram(int idx) {

        statesRepository.setCurrent(idx);

    }

    public ProgramState oneStep(ProgramState state) throws ProgramControllerException {

        IToyStack<IStatement> executionStack = state.getExecutionStack();

        IStatement currentStatement;
        try {
            currentStatement = executionStack.pop();
        } catch (Exception e) {
            throw new ProgramControllerException("The execution stack is empty, there is nothing left to be executed");
        }
        ProgramState newState;
        try {
            newState = currentStatement.execute(state);
        } catch (Exception e) {
            throw new ProgramControllerException(e.getMessage());
        }

        System.out.println(newState.toString());

        return newState;
    }

    public void oneStepCurrentProgram() throws ProgramControllerException {

        ProgramState currentProgram = statesRepository.getCurrentProgram();
        ProgramState executedOnceProgram;
        try {
            executedOnceProgram = oneStep(currentProgram);
        } catch (Exception e) {
            throw new ProgramControllerException(e.getMessage());
        }
        statesRepository.updateProgram(executedOnceProgram);

    }

    public void allStep() throws ProgramControllerException {
        ProgramState program = statesRepository.getCurrentProgram();
        while (true) {
            try {
                oneStep(program);
            } catch (Exception e) {
                throw new ProgramControllerException(e.getMessage());
            }
        }
    }

    public IToyList<String> getCurrentProgramOutput() {

        return statesRepository.getCurrentProgram().getOut();

    }


}
