package model;

import config.AppConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonScoreImplTest {

    AppConfig appConfig = new AppConfig();
    Score score = appConfig.score();

    @Test
    void 점수_저장하기() {
        int length = score.getList().size();
        score.save("adafdfa", 14242);
        assertEquals(length + 1, score.getList().size());
    }

    @Test
    void 점수_초기화() {
        score.resetList();
    }
}