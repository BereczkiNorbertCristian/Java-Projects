package Repository;

import Model.ProgramState;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bnorbert on 22.10.2016.
 * bnorbertcristian@gmail.com
 */
public interface IStateRepository {

    ProgramState getCurrentProgram();

    IStateRepository addProgram(ProgramState programState);

    IStateRepository updateProgram(ProgramState programState);

    void setCurrent(int idx);

    void setLogFile(String logFilePath);

    @Deprecated
    void logProgramState()throws IOException;

    void logProgramStateExec(ProgramState programState) throws IOException;

    void logMessage(String message) throws IOException;

    List<ProgramState> getProgramStates();

    void setProgramList(ArrayList<ProgramState> programStates);


}
