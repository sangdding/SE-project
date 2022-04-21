package controller;

import java.io.Serializable;
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

    public  List<Map.Entry<String, int[]>> orederByAscent(HashMap<String,int[]> hashmap)
    {


        List<Map.Entry<String, int[]>> list = new ArrayList<>(hashmap.entrySet());
        list.sort(com());



        return list;
    }

    public  List<Map.Entry<String, int[]>> orederByDescent(HashMap<String,int[]> hashmap)
    {

        List<Map.Entry<String, int[]>> list =orederByAscent(hashmap);
        Collections.reverse(list);
        return list;
    }



    private Comparator<Map.Entry<String,int[]>> com()
    {
        return (Comparator<Map.Entry<String, int[]>> & Serializable)
                (c1, c2) -> compareTo(c1,c2);
    }
    
    private int compareTo(Map.Entry<String,int[]> o1, Map.Entry<String,int[]> o2)
    {
        return o1.getValue()[0]-o2.getValue()[0];
    }
}
