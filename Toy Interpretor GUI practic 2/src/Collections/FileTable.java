package Collections;

import Exceptions.KeyNotFoundMapException;
import Model.Pair;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by bnorbert on 11.11.2016.
 * bnorbertcristian@gmail.com
 */
public class FileTable implements IToyMap<Integer, Pair<String,BufferedReader>>,Serializable {

    IToyMap<Integer,Pair<String,BufferedReader>> insideTable;

    public FileTable(){
        insideTable=new ToyMap<Integer, Pair<String, BufferedReader>>();
    }

    @Override
    public IToyMap<Integer,Pair<String,BufferedReader>> put(Integer key,Pair<String,BufferedReader> value){

        insideTable.put(key,value);
        return this;
    }

    @Override
    public Collection<Pair<String,BufferedReader>> values(){

        return insideTable.values();
    }

    @Override
    public boolean containsKey(Integer key){
        return insideTable.containsKey(key);
    }

    @Override
    public Pair<String,BufferedReader> lookup(Integer key) throws KeyNotFoundMapException{

        return insideTable.lookup(key);
    }

    @Override
    public int size(){
        return insideTable.size();
    }

    @Override
    public Pair<String,BufferedReader> remove(Integer key){

        return insideTable.remove(key);
    }

    @Override
    public String toString(){
        String outMessage = "";

        Iterator iter = insideTable.getEntries().iterator();
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            outMessage += pair.getKey() + " = " + pair.getValue().toString() + "\n";
        }

        return outMessage;
    }

    @Override
    public Set<Map.Entry<Integer,Pair<String,BufferedReader>>> getEntries(){
        return insideTable.getEntries();
    }

    @Override
    public IToyMap<Integer,Pair<String,BufferedReader>> clone(){

        IToyMap<Integer,Pair<String,BufferedReader>> ret=new ToyMap<Integer,Pair<String,BufferedReader>>();

        Iterator iter = insideTable.getEntries().iterator();
        while(iter.hasNext()){
            Map.Entry pair= (Map.Entry) iter.next();
            ret.put((Integer) pair.getKey(),(Pair<String, BufferedReader>) pair.getValue());
        }
        return ret;
    }

    @Override
    public void checkEqualMaps(IToyMap<String,Integer> cloned){

    }

    @Override
    public Iterator iterator() {
        return insideTable.iterator();
    }
}
