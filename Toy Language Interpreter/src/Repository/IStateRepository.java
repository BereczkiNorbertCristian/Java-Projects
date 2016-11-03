package Repository;

import Model.ProgramState;

/**
 * Created by bnorbert on 22.10.2016.
 * bnorbertcristian@gmail.com
 */
public interface IStateRepository {

    ProgramState getCurrentProgram();

    void addProgram(ProgramState programState);

    void updateProgram(ProgramState programState);

    void setCurrent(int idx);

}
