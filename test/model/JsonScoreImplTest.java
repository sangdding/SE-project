package model;

import config.AppConfig;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class JsonScoreImplTest {

    AppConfig appConfig = new AppConfig();
    Score score = appConfig.score();

    @Test
    void 점수_저장하기() {
        int length = score.getList().size();
        score.save("adfa", 14242);
        assertEquals(length + 1, score.getList().size());
    }

    @Test
    void 파일_없으면_만들기() {
        score.resetList();
    }

}