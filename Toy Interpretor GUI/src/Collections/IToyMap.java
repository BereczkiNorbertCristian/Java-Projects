package Collections;

import Exceptions.KeyNotFoundMapException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by bnorbert on 21.10.2016.
 */
public interface IToyMap<K, V> extends Serializable,Cloneable {

    IToyMap<K,V> put(K key, V value);

    V lookup(K key) throws KeyNotFoundMapException;

    boolean containsKey(K key);

    V remove(K key);

    int size();

    String toString();

    Collection<V> values();

    Set<Map.Entry<K,V>> getEntries();

    IToyMap<K,V> clone();

    void checkEqualMaps(IToyMap<String,Integer> cloned);

    Iterator iterator();
}
