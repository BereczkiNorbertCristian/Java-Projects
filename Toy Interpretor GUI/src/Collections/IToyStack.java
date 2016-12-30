package Collections;

import Exceptions.EmptyStackException;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by bnorbert on 14.10.2016.
 */
public interface IToyStack<T> extends Serializable {

    T pop() throws EmptyStackException;

    IToyStack<T> push(T elem);

    int size();

    T peek();

    boolean empty();

    String toString();

    Iterator<T> iterator();

}
