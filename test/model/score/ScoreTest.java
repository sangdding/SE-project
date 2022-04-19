package model.score;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    private Score score = new JsonScore();

    @Test
    @DisplayName("아이템 스코어 저장")
    void itemModeSave() {
        score.save("hi", 12991, 1);
        Assertions.assertEquals(score.getList(1).get("hi"), 12991);
    }

    @Test
    @DisplayName("노말 스코어 저장")
    void normalModeSave() {
        score.save("hi", 12991, 0);
        Assertions.assertEquals(score.getList(0).get("hi"), 12991);
    }
}
