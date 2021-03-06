package Model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class Pair<K,V> implements Serializable{


    K first;
    V second;

    public Pair(){}

    public Pair(K first,V second){
        this.first=first;
        this.second=second;
    }


    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }


    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }


    @Override
    public String toString(){
        return first.toString();
    }




}