package Controller;

import Collections.*;
import Exceptions.ProgramControllerException;
import Model.*;
import Model.Statements.IStatement;
import Repository.IStateRepository;
import javafx.scene.control.Alert;

import static Controller.GarbageCollector.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by bnorbert on 22.10.2016.
 * bnorbertcristian@gmail.com
 */
public class ProgramController {

    IStateRepository statesRepository;
    ExecutorService executor;
    boolean terminated=false;

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

    public List<ProgramState> getProgramStates(){
        return statesRepository.getProgramStates();
    }

    @Deprecated
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

    @Deprecated
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

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates){

        return programStates.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> programList) throws Exception{

        if(terminated){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Program stopped due to failure");
            alert.showAndWait();
            return;
        }


        programList.forEach(prg -> {
                try {
                    statesRepository.logProgramStateExec(prg);
                }
                catch (IOException ioe){
                    System.out.println(ioe.getMessage());
                }
        }
        );
        List<Callable<ProgramState>> callableList;

        try {
            callableList = programList.stream()
                    .map((ProgramState p) -> (Callable<ProgramState>) (() -> {
                        return p.oneStep();
                    }))
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            System.out.println("Exception caught");
            throw new Exception(e.getMessage());
        }
        List<ProgramState> newProgramStates=null;
        try{
            newProgramStates = executor.invokeAll(callableList)
                    .stream()
                    .map(future -> {
                        try{
                            return future.get();
                        }
                        catch (InterruptedException ie){
                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("ERROR:"+ie.getMessage());
                            alert.showAndWait();
                            terminated=true;
                            return null;
                        }
                        catch (ExecutionException ee){
                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("ERROR:"+ee.getMessage());
                            alert.showAndWait();
                            terminated=true;
                            return null;
                        }
                        catch (Exception e){
                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("ERROR:"+e.getMessage());
                            alert.showAndWait();
                            terminated=true;
                            return null;
                        }
                    })
                    .filter(p -> p!= null)
                    .collect(Collectors.toList());
        }
        catch (InterruptedException ie){
            ie.printStackTrace();
        }
        catch (Exception e){
            System.out.println("Exception caught");
        }

        //showList(programList);
        //showList(newProgramStates);
        //System.out.println("another one step\n");

        programList.addAll(newProgramStates);
        programList.forEach(prg ->{
            try{
                statesRepository.logProgramStateExec(prg);
            }
            catch (IOException ioe){
                ioe.printStackTrace();
            }
        });

        statesRepository.setProgramList((ArrayList<ProgramState>)programList);
    }

    public void allStepExecute(){
        executor= Executors.newFixedThreadPool(2);
        while(true){
            List<ProgramState> programList=removeCompletedPrograms(statesRepository.getProgramStates());
            if(programList.size() == 0){
                break;
            }
            try{
                oneStepForAllPrograms(programList);
            }
            catch (Exception e){
                continue;
            }
        }
        executor.shutdownNow();
    }



    public void showList(List<ProgramState> lst){
        System.out.println("------------------------");
        for(ProgramState prg : lst){
            System.out.println(prg.toString());
        }
        System.out.println("------------------------");
    }

    public void allStepGUI(){


        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList=removeCompletedPrograms(statesRepository.getProgramStates());
        if(programStateList.size() == 0){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("PROGRAM HAS FINISHED!");
            alert.showAndWait();
            executor.shutdownNow();
        }
        else{
            try {
                oneStepForAllPrograms(programStateList);
            }
            catch (Exception e){
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("PROGRAM HAS FINISHED!" + e.getMessage());
                alert.showAndWait();
            }

            executor.shutdownNow();
        }
    }

}
