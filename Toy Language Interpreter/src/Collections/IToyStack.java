package Collections;

import Exceptions.EmptyStackException;

/**
 * Created by bnorbert on 14.10.2016.
 */
public interface IToyStack<T> {

    T pop() throws EmptyStackException;

    void push(T elem);

    int size();

    T peek();

    boolean empty();

    String toString();

}
