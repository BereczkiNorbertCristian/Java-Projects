package Collections;

import Collections.IToyMap;
import Exceptions.KeyNotFoundMapException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bnorbert on 21.10.2016.
 */
public class ToyMap<K, V> implements IToyMap<K, V> {

    Map<K, V> insideMap;

    public ToyMap() {
        insideMap = new HashMap<K, V>();
    }

    @Override
    public void put(K key, V value) {
        insideMap.put(key, value);
    }

    @Override
    public V lookup(K key) throws KeyNotFoundMapException {

        if (!insideMap.containsKey(key)) {
            throw new KeyNotFoundMapException("Key not found");
        }
        return insideMap.get(key);
    }

    @Override
    public V remove(K key) {
        V v = insideMap.remove(key);
        return v;
    }

    @Override
    public int size() {
        return insideMap.size();
    }

    @Override
    public String toString() {

        String outMessage = "";

        Iterator iter = insideMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            outMessage += pair.getKey() + " = " + pair.getValue() + "\n";
        }

        return outMessage;
    }


}
