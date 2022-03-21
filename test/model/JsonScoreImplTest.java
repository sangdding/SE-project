package model;

import config.AppConfig;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class JsonScoreImplTest {

    AppConfig appConfig = new AppConfig();
    Score score = appConfig.score();

    @Test
    void 파일_없으면_만들기() {

    }

}