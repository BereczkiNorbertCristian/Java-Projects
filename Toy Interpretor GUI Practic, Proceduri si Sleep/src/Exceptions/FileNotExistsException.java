package Exceptions;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class FileNotExistsException extends Exception {

    public FileNotExistsException(){}
    public FileNotExistsException(String message){
        super(message);
    }
}
