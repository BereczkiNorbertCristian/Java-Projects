package Collections;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by bnorbert on 21.10.2016.
 */
public interface IToyList<T> extends Serializable {

    IToyList<T> add(T element);

    T remove(int idx);

    int size();

    void clear();

    T get(int idx);

    String toString();

    Iterator iterator();
}
