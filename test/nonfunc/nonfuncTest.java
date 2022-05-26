package nonfunc;

import model.setting.JsonSetting;
import model.setting.Setting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.GamePage;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class nonfuncTest {

    Setting setting = new JsonSetting("player1");

    @Test
    @DisplayName("키 입력 속도 측정")
    void checkKeyProcessTime() {
        setting.setGameMode(0);
        GamePage.GamePageKeyListener keyListener = new GamePage().new GamePageKeyListener();
        JButton jButtons = new JButton();
        long beforeTime = System.currentTimeMillis();
        KeyEvent keyEvent = new KeyEvent(jButtons, 0, 0, 1, setting.getKeyList().get("down"));
        keyListener.keyPressed(keyEvent);
        long afterTime = System.currentTimeMillis();
        long resultTime = afterTime - beforeTime;
        System.out.println(resultTime);
        Assertions.assertTrue(resultTime < 1000);
    }


}
