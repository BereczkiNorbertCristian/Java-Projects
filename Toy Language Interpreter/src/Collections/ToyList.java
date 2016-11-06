package Collections;

import Collections.IToyList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bnorbert on 21.10.2016.
 */
public class ToyList<T> implements IToyList<T> {

    List<T> insideList;

    public ToyList() {
        insideList = new LinkedList<T>();
    }

    @Override
    public IToyList<T> add(T element) {
        insideList.add(element);
        return this;
    }

    @Override
    public int size() {
        return insideList.size();
    }

    @Override
    public void clear() {
        insideList.clear();
    }

    @Override
    public T get(int idx) {
        return insideList.get(idx);
    }

    @Override
    public T remove(int idx) {
        return insideList.remove(idx);
    }

    @Override
    public String toString() {

        String outMessage = "";

        Iterator iter = insideList.iterator();
        while (iter.hasNext()) {
            outMessage += iter.next() + "\n";
        }
        return outMessage;
    }

}
