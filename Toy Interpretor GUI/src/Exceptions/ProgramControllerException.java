package Exceptions;

import Controller.ProgramController;

/**
 * Created by bnorbert on 02.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ProgramControllerException extends Exception {

    public ProgramControllerException() {
    }

    public ProgramControllerException(String message) {
        super(message);
    }

}
