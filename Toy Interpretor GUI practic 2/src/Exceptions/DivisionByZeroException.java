package Exceptions;

/**
 * Created by bnorbert on 28.10.2016.
 * bnorbertcristian@gmail.com
 */
public class DivisionByZeroException extends Exception {

    public DivisionByZeroException() {
    }

    public DivisionByZeroException(String message) {
        super(message);
    }
}
