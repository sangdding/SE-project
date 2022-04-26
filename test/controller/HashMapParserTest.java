package controller;

import model.score.JsonScore;
import model.score.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HashMapParserTest {

    Score score = new JsonScore();
    HashMapParser hashMapParser = new HashMapParser();

    @Test
    @DisplayName("정렬 테스트")
    void sortTest() {
        HashMap<String, Integer> testMap = score.getScorelist(0);
        List<Map.Entry<String, Integer>> resultList =  hashMapParser.orederByDescent(testMap);
        for (int i = 0; i < resultList.size()-1; i++) {
            assertTrue(resultList.get(i).getValue() >= resultList.get(i+1).getValue());
        }
    }

}