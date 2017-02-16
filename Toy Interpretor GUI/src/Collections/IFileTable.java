package Collections;

import Exceptions.KeyNotFoundMapException;
import Model.Pair;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by bnorbert on 24.01.2017.
 * bnorbertcristian@gmail.com
 */
public interface IFileTable {


    public IFileTable put(Integer key,Pair<String,BufferedReader> value);


    public Collection<Pair<String,BufferedReader>> values();

    public boolean containsKey(Integer key);

    public Pair<String,BufferedReader> lookup(Integer key) throws KeyNotFoundMapException;

    public int size();

    public Pair<String,BufferedReader> remove(Integer key);



    public Set<Map.Entry<Integer,Pair<String,BufferedReader>>> getEntries();


    public Iterator iterator();
}
