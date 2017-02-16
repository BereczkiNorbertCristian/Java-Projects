package Collections;

import Exceptions.IllegalMemoryAccessException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bnorbert on 24.11.2016.
 * bnorbertcristian@gmail.com
 */
public class Heap<U> implements Serializable {

    //heap is a map which maps an address to its value at that address
    private Map<Integer,U> heap;
    private UniqueTrie uniqueProvider;

    public Heap(){
        heap=new HashMap<>();
        uniqueProvider=new UniqueTrie(17);
        uniqueProvider.insertValue(0);
    }

    public int put(U value){

        Integer addressWrapper=new Integer(
                uniqueProvider.uniqueValue()
        );

        heap.put(addressWrapper,value);

        return addressWrapper.intValue();
    }

    public U get(int address) throws IllegalMemoryAccessException{

        if (address == 0){
            throw new IllegalMemoryAccessException("You cannot access the 0 address");
        }

        Integer addressWrapper=new Integer(address);

        if(!heap.containsKey(addressWrapper)){
            throw new IllegalMemoryAccessException("No memory was allocated at this address");
        }

        return heap.get(addressWrapper);
    }

    public void updateAtAddress(Integer address,U value){

        if(address.intValue() !=0 ) {
            heap.put(address, value);
        }

    }

    public void remove(int address){

        if(address != 0) {
            Integer addressWrapper = new Integer(address);
            heap.remove(addressWrapper);
            uniqueProvider.removeValue(address);
        }
    }

    public void setContent(Map<Integer,U> newContent){
        heap=newContent;
    }

    @Override
    public String toString(){

        String message = "";

        Iterator iter = heap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry pair=(Map.Entry) iter.next();
            message += pair.getKey().toString() + " = " + pair.getValue().toString() + "\n";
        }

        return message;
    }

    public Map<Integer,U> getInsideMap(){
        return heap;
    }

    public Iterator iterator(){
        return heap.entrySet().iterator();
    }

}
