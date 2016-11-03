package Mocks;

import Collections.ToyMap;

/**
 * Created by bnorbert on 02.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ToyMapMock extends ToyMap<String,Integer> {

    @Override
    public Integer lookup(String key){

        if(key == "a"){
            return 3;
        }
        if(key == "b"){
            return 4;
        }
        return 0;
    }
}
