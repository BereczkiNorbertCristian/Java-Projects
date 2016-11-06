package Exceptions;

/**
 * Created by bnorbert on 22.10.2016.
 */
public class VariableNotDefinedException extends Exception {

    public VariableNotDefinedException() {
    }

    public VariableNotDefinedException(String message) {
        super(message);
    }

}
