package Repository;

import Model.ProgramState;

import java.io.IOException;

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

    void logProgramState()throws IOException;

}
