package Model;

/**
 * Created by bnorbert on 14.10.2016.
 */
public interface IStatement {

    ProgramState execute(ProgramState programState) throws Exception;
}
