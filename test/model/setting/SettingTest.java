package model.setting;

import config.AppConfig;
import model.score.Score;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;

import static java.awt.event.KeyEvent.VK_I;
import static org.junit.jupiter.api.Assertions.*;

class SettingTest {

    AppConfig appConfig = new AppConfig();
    Setting setting = appConfig.setting();

    @Test
    void 키_세팅정보_가져오기() {
        HashMap<String, Integer> keySet = setting.getKeyList();
        Assertions.assertEquals(9, keySet.size());
    }

    @Test
    void 키_세팅정보_저장하기() {
        HashMap<String, Integer> changekeySet = setting.getDefaultKeySet();
        changekeySet.put("up", VK_I);
        setting.setKeyList(changekeySet);
        Assertions.assertEquals(VK_I, setting.getKeyList().get("up"));
    }

    @Test
    void 기본키_세팅_가져오기() {
        HashMap<String, Integer> keySet = setting.getDefaultKeySet();
        Assertions.assertEquals(9, keySet.size());
    }

    @Test
    void 기본키로_세팅하기() {
        setting.setDefaultKeySet();
    }

    @Test
    void 화면크기_가져오기() {
        HashMap<String, Integer> displaySet = setting.getDisplaySize();
        Assertions.assertEquals(2, displaySet.size());
    }

    @Test
    void 화면크기_설정하기() {
        setting.setDisplaySize(1000, 1000);
        HashMap<String, Integer> displaySet = setting.getDisplaySize();
        Assertions.assertEquals(1000, displaySet.get("width"));
    }


    @Test
    void 난이도_설정하기() {
        setting.setDifficulty(0);
        Assertions.assertEquals(0, setting.getDifficulty());

        setting.setDifficulty(1);
        Assertions.assertEquals(1, setting.getDifficulty());

        setting.setDifficulty(2);
        Assertions.assertEquals(2, setting.getDifficulty());
    }


    @Test
    void 화면표시모드_설정하기() {
        setting.setDisplayMode(0);
        System.out.println(setting.getDisplayMode());
        Assertions.assertEquals(0, setting.getDisplayMode());
//        setting.setDisplayMode(1);
//        System.out.println(setting.getDisplayMode());
//        Assertions.assertEquals(1, setting.getDisplayMode());
    }
}