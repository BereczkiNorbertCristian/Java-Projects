package Model.Statements;

import Model.ProgramState;

import java.io.Serializable;

/**
 * Created by bnorbert on 14.10.2016.
 */
public interface IStatement extends Serializable {

    ProgramState execute(ProgramState programState) throws Exception;
}
