package model;

import java.util.HashMap;
import java.util.Map;

public interface Score {

    void save(String name, int score); // 점수 정보 저장

    HashMap<String, Integer> getList(); // 점수 정보 불러오기

    void resetList(); // 점수 정보 초기화하기
}
