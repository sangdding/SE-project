package controller;

import java.util.HashMap;
import java.util.*;

public class HashMapParser {

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