package Exceptions;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class FileExistException extends Exception {

    public FileExistException(){}
    public FileExistException(String message){
        super(message);
    }

}
