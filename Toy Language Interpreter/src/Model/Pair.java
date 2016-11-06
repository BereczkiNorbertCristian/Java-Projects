package Model;

import java.util.Map;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class Pair<K,V>{

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

    K first;

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    V second;





}