package controller;

import config.AppConfig;
import model.Score;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ScoreLogicTest {

    AppConfig appConfig = new AppConfig();
    Score score = appConfig.score();
    ScoreLogic scoreLogic = new ScoreLogic();

    @Test
    public void 저장_테스트() {

        String name1 = "이상명";
        int score1 = 50;
        String name2 = "이해랑";
        int score2 = 150;
        String name3 = "정현철";
        int score3 = 125;
        String name4 = "서민재";
        int score4 = 185;

        this.score.save(name1, score1);
        this.score.save(name2, score2);
        this.score.save(name3, score3);
        this.score.save(name4, score4);

        Assertions.assertEquals(4, this.score.getList().size());
    }

    @Test
    public void 정렬_테스트() {
        저장_테스트();
        HashMap<String, Integer> resultList = scoreLogic.sortByAscend(this.score.getList());
        int testValue = 200;
        for(int score : resultList.values()) {
            System.out.println(score);
            assertTrue(testValue > score);
            testValue = score;
        }
    }
}