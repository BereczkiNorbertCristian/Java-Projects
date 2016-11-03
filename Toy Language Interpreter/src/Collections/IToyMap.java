package Collections;

import Exceptions.KeyNotFoundMapException;

/**
 * Created by bnorbert on 21.10.2016.
 */
public interface IToyMap<K, V> {

    void put(K key, V value);

    V lookup(K key) throws KeyNotFoundMapException;

    V remove(K key);

    int size();

    String toString();

}
