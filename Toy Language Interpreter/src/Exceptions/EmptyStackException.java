package Exceptions;

/**
 * Created by bnorbert on 21.10.2016.
 */
public class EmptyStackException extends Exception {

    public EmptyStackException() {
    }

    public EmptyStackException(String message) {
        super(message);
    }
}
