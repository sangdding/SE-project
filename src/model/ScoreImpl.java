package model;

import java.util.HashMap;
import java.util.Map;

public class ScoreImpl implements Score{

    private String name;
    private int score;
    private final HashMap<Long, Integer> scoreInfo = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public void save(String name, int score) {
        scoreInfo.put(++sequence, score);
    }

    @Override
    public HashMap<Long, Integer> getList() {
        return scoreInfo;
    }
}
