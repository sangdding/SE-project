package view;

import model.setting.JsonSetting;
import model.setting.Setting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class VersusGamePageTest {

    Setting settingP1 = new JsonSetting("player1");
    Setting settingP2 = new JsonSetting("player2");

    @Test
    @DisplayName("키 입력 테스트-노말")
    void keyPressedTest() {
        settingP1.setGameMode(0);
        settingP2.setGameMode(0);
        VersusGamePage.VersusGamePageKeyListener keyListener = new VersusGamePage().new VersusGamePageKeyListener();
        JButton jButtons = new JButton();
        KeyEvent keyEvent = new KeyEvent(jButtons, 0, 0, 1, settingP1.getKeyList().get("down"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("drop"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("rotate"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("right"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("left"));
        keyListener.keyPressed(keyEvent);

        keyEvent.setKeyCode(settingP2.getKeyList().get("drop"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP2.getKeyList().get("rotate"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP2.getKeyList().get("right"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP2.getKeyList().get("left"));
        keyListener.keyPressed(keyEvent);
        keyListener.keyPressed(keyEvent);
    }

    @Test
    @DisplayName("키 입력 테스트-아이템")
    void itemKeyPressedTest() {
        settingP1.setGameMode(1);
        settingP2.setGameMode(1);
        VersusGamePage.VersusGamePageKeyListener keyListener = new VersusGamePage().new VersusGamePageKeyListener();
        JButton jButtons = new JButton();
        KeyEvent keyEvent = new KeyEvent(jButtons, 0, 0, 1, settingP1.getKeyList().get("down"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("drop"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("rotate"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("right"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("left"));
        keyListener.keyPressed(keyEvent);

        keyEvent.setKeyCode(settingP2.getKeyList().get("drop"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP2.getKeyList().get("rotate"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP2.getKeyList().get("right"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP2.getKeyList().get("left"));
        keyListener.keyPressed(keyEvent);
        keyListener.keyPressed(keyEvent);
    }

    @Test
    @DisplayName("키 입력 테스트-타이머")
    void TimerKeyPressedTest() {
        settingP1.setGameMode(2);
        settingP2.setGameMode(2);
        VersusGamePage.VersusGamePageKeyListener keyListener = new VersusGamePage().new VersusGamePageKeyListener();
        JButton jButtons = new JButton();
        KeyEvent keyEvent = new KeyEvent(jButtons, 0, 0, 1, settingP1.getKeyList().get("down"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("drop"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("rotate"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("right"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP1.getKeyList().get("left"));
        keyListener.keyPressed(keyEvent);

        keyEvent.setKeyCode(settingP2.getKeyList().get("drop"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP2.getKeyList().get("rotate"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP2.getKeyList().get("right"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(settingP2.getKeyList().get("left"));
        keyListener.keyPressed(keyEvent);
        keyListener.keyPressed(keyEvent);
    }

    @Test
    @DisplayName("게임 종료화면")
    void endTest() {
        settingP1.setGameMode(1);
        settingP2.setGameMode(1);
        VersusGamePage.VersusGamePageKeyListener keyListener = new VersusGamePage().new VersusGamePageKeyListener();
        JButton jButtons = new JButton();
        KeyEvent keyEvent = new KeyEvent(jButtons, 0, 0, 1, settingP1.getKeyList().get("down"));
        keyListener.keyPressed(keyEvent);
    }

}