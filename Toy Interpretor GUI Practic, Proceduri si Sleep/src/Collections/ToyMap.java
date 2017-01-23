package Collections;

import Collections.IToyMap;
import Exceptions.KeyNotFoundMapException;

import java.io.Serializable;
import java.util.*;

/**
 * Created by bnorbert on 21.10.2016.
 */
public class ToyMap<K, V> implements IToyMap<K, V>,Serializable {

    Map<K, V> insideMap;

    public ToyMap() {
        insideMap = new HashMap<K, V>();
    }

    @Override
    public IToyMap<K,V> put(K key, V value) {
        insideMap.put(key, value);
        return this;
    }

    @Override
    public boolean containsKey(K key){
        return insideMap.containsKey(key);
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

    @Override
    public Collection<V> values(){
        return insideMap.values();
    }

    @Override
    public Set<Map.Entry<K,V>> getEntries(){
        return insideMap.entrySet();
    }

    @Override
    public IToyMap<K,V> clone(){

        ToyMap<K,V> ret=new ToyMap<K, V>();

        Iterator iter = insideMap.entrySet().iterator();
        while( iter.hasNext()){
            Map.Entry pair = (Map.Entry) iter.next();
            ret.put((K)pair.getKey(),(V)pair.getValue());
        }
        return ret;
    }


    @Override
    public Iterator iterator(){
        return insideMap.entrySet().iterator();
    }
}
