package Collections;

import Exceptions.KeyNotFoundMapException;
import Model.Pair;

import java.io.BufferedReader;
import java.util.Collection;

/**
 * Created by bnorbert on 11.11.2016.
 * bnorbertcristian@gmail.com
 */
public class FileTable implements IToyMap<Integer, Pair<String,BufferedReader>> {

    IToyMap<Integer,Pair<String,BufferedReader>> insideTable;

    public FileTable(){
        insideTable=new ToyMap<>();
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

}
