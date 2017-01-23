package Collections;

import Exceptions.KeyNotFoundMapException;
import Model.ProcMapping;

import java.util.*;

/**
 * Created by bnorbert on 23.01.2017.
 * bnorbertcristian@gmail.com
 */
public class ProcTable implements IToyMap<String, ProcMapping> {

    Map<String,ProcMapping> insideMap;

    public ProcTable(){
        insideMap=new HashMap<>();
    }

    @Override
    public ProcTable put(String procedure,ProcMapping procMapping){
        insideMap.put(procedure,procMapping);
        return this;
    }

    @Override
    public boolean containsKey(String procedure){
        return insideMap.containsKey(procedure);
    }

    @Override
    public ProcMapping lookup(String procedure)throws KeyNotFoundMapException{
        if(!insideMap.containsKey(procedure)){
            throw new KeyNotFoundMapException("The procedure is not defined");
        }
        return insideMap.get(procedure);
    }

    @Override
    public ProcMapping remove(String procedure){
        ProcMapping procMapping=insideMap.remove(procedure);
        return procMapping;
    }

    @Override
    public int size(){
        return insideMap.size();
    }

    @Override
    public String toString(){

        String outMessage="";
        Iterator<Map.Entry<String,ProcMapping>> iter=insideMap.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry pair=iter.next();
            outMessage += pair.getKey() + "->" + pair.getValue() + "\n";
        }
        return outMessage;
    }

    @Override
    public Collection<ProcMapping> values(){
        return insideMap.values();
    }

    @Override
    public Set<Map.Entry<String,ProcMapping>> getEntries(){
        return insideMap.entrySet();
    }



    public ProcTable clone(){
        ProcTable ret=new ProcTable();

        Iterator iter = insideMap.entrySet().iterator();
        while( iter.hasNext()){
            Map.Entry pair = (Map.Entry) iter.next();
            ret.put((String) pair.getKey(),(ProcMapping) pair.getValue());
        }
        return ret;
    }

    @Override
    public Iterator iterator(){
        return insideMap.entrySet().iterator();
    }

}
