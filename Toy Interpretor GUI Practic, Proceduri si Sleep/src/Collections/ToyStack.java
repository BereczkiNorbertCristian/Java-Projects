package Collections;

import Collections.IToyStack;
import Exceptions.EmptyStackException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by bnorbert on 14.10.2016.
 */

public class ToyStack<T> implements IToyStack<T>,Serializable {

    Stack<T> insideStack;

    public ToyStack() {
        insideStack = new Stack<T>();
    }

    @Override
    public IToyStack<T> push(T element) {
        insideStack.push(element);
        return this;
    }

    @Override
    public T pop() throws EmptyStackException {
        if (insideStack.size() == 0) {
            throw new EmptyStackException("The stack is empty");
        }
        return insideStack.pop();

    }

    @Override
    public int size() {
        return insideStack.size();
    }

    @Override
    public T peek() {
        return insideStack.peek();
    }

    @Override
    public boolean empty() {
        return insideStack.empty();
    }

    @Override
    public String toString() {

        String outMessage = "";
        Iterator<T> iter = insideStack.iterator();

        while (iter.hasNext()) {
            outMessage += iter.next().toString() + "\n";
        }

        return outMessage;
    }

    @Override
    public Iterator<T> iterator(){
        return insideStack.iterator();
    }


}
