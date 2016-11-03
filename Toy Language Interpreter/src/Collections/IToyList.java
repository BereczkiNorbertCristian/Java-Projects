package Collections;

/**
 * Created by bnorbert on 21.10.2016.
 */
public interface IToyList<T> {

    void add(T element);

    T remove(int idx);

    int size();

    void clear();

    T get(int idx);

    String toString();
}
