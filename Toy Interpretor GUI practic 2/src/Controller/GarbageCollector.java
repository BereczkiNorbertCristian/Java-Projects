package Controller;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by bnorbert on 25.11.2016.
 * bnorbertcristian@gmail.com
 */
public class GarbageCollector {

    public static Map<Integer,Integer> conservativeGarbageCollector(Collection<Integer> symbolTableValues,
                                                                    Map<Integer,Integer> heap){

        return heap.entrySet().stream()
                .filter(e->symbolTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }
}
