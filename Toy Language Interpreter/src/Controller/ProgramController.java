package Controller;

import Collections.*;
import Exceptions.ProgramControllerException;
import Model.*;
import Model.Statements.IStatement;
import Repository.IStateRepository;

import static Controller.GarbageCollector.*;

import java.io.IOException;

/**
 * Created by bnorbert on 22.10.2016.
 * bnorbertcristian@gmail.com
 */
public class ProgramController {

    IStateRepository statesRepository;

    String programOuput;

    public ProgramController(IStateRepository statesRepository) {
        this.statesRepository = statesRepository;
        this.programOuput ="";
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
            String attachedLogError="";
            try {
                statesRepository.logMessage(e.getMessage());
            }
            catch (IOException ioe){
                attachedLogError=ioe.getMessage();
            }
            throw new ProgramControllerException("The execution stack is empty, there is nothing left to be executed " + attachedLogError);
        }
        ProgramState newState;
        try {
            newState = currentStatement.execute(state);
        } catch (Exception e) {
            String attachedLogError="";
            try {
                statesRepository.logMessage(e.getMessage());
            }
            catch (IOException ioe){
                attachedLogError=ioe.getMessage();
            }
            throw new ProgramControllerException(e.getMessage()+attachedLogError);
        }

        //System.out.println(newState.toString());

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

    public ProgramState getCurrentProgram() {
        return statesRepository.getCurrentProgram();
    }

    public String allStep() throws ProgramControllerException {

        ProgramState program = statesRepository.getCurrentProgram();
        try {
            program.serialize();
        }
        catch (Exception e){
            throw new ProgramControllerException(e.getMessage());
        }
        while (true) {
            try {
                oneStep(program);
                program.getHeap().setContent(conservativeGarbageCollector(program.getSymbolTable().values(),program.getHeap().getInsideMap()));


                programOuput +=getCurrentProgram().toString();
                statesRepository.logProgramState();
            } catch (Exception e) {
                try {
                    statesRepository.getCurrentProgram().setState(program.obtainSerializable());
                }
                catch (Exception ee) {
                    throw new ProgramControllerException(ee.getMessage());
                }
                throw new ProgramControllerException(e.getMessage());
            }

        }
    }

    public String getProgramOuput(){
        return programOuput;
    }

    public IToyList<String> getCurrentProgramOutput() {

        return statesRepository.getCurrentProgram().getOut();

    }

    public void setLogFile(String logFilePath){
        statesRepository.setLogFile(logFilePath);
    }



}
