package Collections;

import Exceptions.KeyNotFoundMapException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by bnorbert on 25.01.2017.
 * bnorbertcristian@gmail.com
 */
public interface ILockTable {

    ILockTable synchronizedUnion(Integer key, Integer value);

    Integer lookup(Integer key) throws KeyNotFoundMapException;

    boolean containsKey(Integer key);

    Integer remove(Integer key);

    int size();

    String toString();

    Collection<Integer> values();

    Set<Map.Entry<Integer,Integer>> getEntries();

    Iterator iterator();

    Integer getFreeLocation();

}
