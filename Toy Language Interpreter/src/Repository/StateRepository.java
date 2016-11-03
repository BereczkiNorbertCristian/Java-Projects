package Repository;

import Model.ProgramState;

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

    public StateRepository() {
        states = new ArrayList<ProgramState>();
        currIdx = 0;
    }

    @Override
    public void addProgram(ProgramState programState) {

        states.add(programState);
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
    public void updateProgram(ProgramState programState) {
        states.set(currIdx, programState);
    }

}
