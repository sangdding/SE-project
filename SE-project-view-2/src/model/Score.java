package model;

import java.util.HashMap;
import java.util.Map;

public interface Score {

    public void save(String name, int score);
    public HashMap<Long, Integer> getList();

}
