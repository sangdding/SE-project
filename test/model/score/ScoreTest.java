package model.score;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    private Score score = new JsonScore();
    private String name = "DAFDD";
    private int scoreVal = 122412;

    @Test
    @DisplayName("아이템 스코어 저장")
    void itemModeSave() {
        score.save(name, scoreVal, 1, 1);
        Assertions.assertEquals(score.getScorelist(1).get(name), scoreVal);
    }

    @Test
    @DisplayName("노말 스코어 저장")
    void normalModeSave() {
        score.save("AAAA", 12991, 0, 1);
        Assertions.assertEquals(score.getScorelist(0).get(name), scoreVal);
    }

    @Test
    @DisplayName("스코어 리스트 가져오기")
    void getList() {
        score.getScorelist(1);
    }

    @Test
    @DisplayName("스코어보드 리셋")
    void resetScoreBoard() {
        score.resetList(0);
        Assertions.assertEquals(1, score.getScorelist(0).size());

        score.resetList(1);
        Assertions.assertEquals(1, score.getScorelist(0).size());
    }
}
