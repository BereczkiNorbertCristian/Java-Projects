package Exceptions;

/**
 * Created by bnorbert on 22.10.2016.
 * bnorbertcristian@gmail.com
 */
public class EmptyStackControllerException extends Exception {

    public EmptyStackControllerException() {
    }

    public EmptyStackControllerException(String message) {
        super(message);
    }

}
