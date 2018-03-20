package LexicalAnalyser.BaseModels;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alyswidan on 19/03/18.
 */
class MultiMapIteratorTest {
    @Test
    void hasNext() {
    }

    @Test
    void next() {
        Map<String,List<Integer>> m = new HashMap<>();
        m.put("a", Arrays.asList(1,2,3,4,5));
        m.put("b", Arrays.asList(6,7,8,9,10));
        m.put("c", Arrays.asList(11,21,31,41,51));

        MultiMap<String,Integer> multiMap = new MultiMap<>();
        multiMap.put("a",1);
        multiMap.put("a",2);
        multiMap.put("a",3);
        multiMap.put("a",4);
        multiMap.put("a",5);

        multiMap.put("b",11);
        multiMap.put("b",11);
        multiMap.put("b",11);
        multiMap.put("b",11);
        multiMap.put("b",11);



    }

}