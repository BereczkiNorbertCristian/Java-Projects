package Repository;

import Model.ProgramState;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bnorbert on 22.10.2016.
 * bnorbertcristian@gmail.com
 */
public class StateRepository implements IStateRepository {

    List<ProgramState> states;
    int currIdx;
    File logFilePath;

    public StateRepository() throws IOException {
        states = new ArrayList<ProgramState>();
        currIdx = 0;
        logFilePath=null;
    }

    @Override
    public IStateRepository addProgram(ProgramState programState) {

        states.add(programState);
        return this;
    }

    @Override
    public void setLogFile(String logFilePath){

        this.logFilePath=new File(logFilePath);
    }


    @Override
    public void setCurrent(int idx) {
        this.currIdx = idx;
    }


    @Override
    public ProgramState getCurrentProgram() {
        return states.get(currIdx);
    }

    @Override
    public IStateRepository updateProgram(ProgramState programState) {
        states.set(currIdx, programState);
        return this;
    }

    @Override
    public void logProgramState() throws IOException{

        if(logFilePath != null) {

            Writer logFileWriter = new PrintWriter(new FileWriter(logFilePath, true));

            logFileWriter.write(getCurrentProgram().toString());

            logFileWriter.close();
        }
    }

    @Override
    public void logMessage(String message) throws IOException{

        if(logFilePath != null){

            Writer logFileWriter = new PrintWriter(new FileWriter(logFilePath,true));

            logFileWriter.write(message+"\n");

            logFileWriter.close();

        }

    }

}
