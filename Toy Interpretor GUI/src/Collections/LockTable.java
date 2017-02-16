package Collections;

import Exceptions.KeyNotFoundMapException;

import java.util.*;

/**
 * Created by bnorbert on 25.01.2017.
 * bnorbertcristian@gmail.com
 */
public class LockTable implements ILockTable {

    Map<Integer,Integer> insideMap;
    Integer locationCounter=0;

    public LockTable(){
        insideMap=new HashMap<>();
    }
    @Override
    public ILockTable synchronizedUnion(Integer key, Integer value) {
        insideMap.put(key, value);
        return this;
    }

    @Override
    public boolean containsKey(Integer key){
        return insideMap.containsKey(key);
    }

    @Override
    public Integer lookup(Integer key) throws KeyNotFoundMapException {

        if (!insideMap.containsKey(key)) {
            throw new KeyNotFoundMapException("There is no lock on this object" + Integer.toString(key));
        }
        return insideMap.get(key);
    }

    @Override
    public Integer remove(Integer key) {
        Integer v = insideMap.remove(key);
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
            outMessage += pair.getKey().toString() + " = " + pair.getValue().toString() + "\n";
        }

        return outMessage;
    }

    @Override
    public Collection<Integer> values(){
        return insideMap.values();
    }

    @Override
    public Set<Map.Entry<Integer,Integer>> getEntries(){
        return insideMap.entrySet();
    }


    @Override
    public Iterator iterator(){
        return insideMap.entrySet().iterator();
    }

    @Override
    public Integer getFreeLocation(){
        locationCounter++;
        return locationCounter;
    }


}
