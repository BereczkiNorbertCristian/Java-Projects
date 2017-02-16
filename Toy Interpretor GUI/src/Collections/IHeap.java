package Collections;

import Exceptions.IllegalMemoryAccessException;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by bnorbert on 24.01.2017.
 * bnorbertcristian@gmail.com
 */
public interface IHeap<U> {

    public int put(U value);

    public U get(int address) throws IllegalMemoryAccessException;

    public void updateAtAddress(Integer address,U value);

    public void remove(int address);

    public void setContent(Map<Integer,U> newContent);


    public Map<Integer,U> getInsideMap();

    public Iterator iterator();
}
