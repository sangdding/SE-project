package view;

import java.util.HashMap;
import java.util.*;

public class HashMapParser {

    public ArrayList getValues(HashMap<String,Integer> hashmap)
    {

        ArrayList<Integer> values = new ArrayList<Integer>();

        Iterator<String> iter = hashmap.keySet().iterator();
        while(iter.hasNext()) {
            String key = iter.next();
            values.add(hashmap.get(key));
        }
        return values;
    }
}
