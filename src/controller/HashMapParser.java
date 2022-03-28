package controller;

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

    public  List<Map.Entry<String, Integer>> orederByAscent(HashMap<String,Integer> hashmap)
    {

        List<Map.Entry<String, Integer>> list = new ArrayList<>(hashmap.entrySet());
        list.sort(Map.Entry.comparingByValue());



        return list;
    }

    public  List<Map.Entry<String, Integer>> orederByDescent(HashMap<String,Integer> hashmap)
    {
        List<Map.Entry<String, Integer>> list =orederByAscent(hashmap);
        Collections.reverse(list);
        return list;
    }


}
