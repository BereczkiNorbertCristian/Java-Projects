package Exceptions;

import View.Commands.ExitCommand;

import java.io.Serializable;

/**
 * Created by bnorbert on 02.12.2016.
 * bnorbertcristian@gmail.com
 */
public class SerializationException extends Exception {

    public SerializationException(){super();}
    public SerializationException(String message){
        super(message);
    }

}
