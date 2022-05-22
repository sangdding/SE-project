package view;

import model.setting.JsonSetting;
import model.setting.Setting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class GamePageTest {

    Setting setting = new JsonSetting("player1");

    @Test
    @DisplayName("키 입력 테스트-노말")
    void keyPressedTest() throws NoSuchFieldException {
        setting.setGameMode(0);
        GamePage.GamePageKeyListener keyListener = new GamePage().new GamePageKeyListener();
        JButton jButtons = new JButton();
        KeyEvent keyEvent = new KeyEvent(jButtons, 0, 0, 1, setting.getKeyList().get("down"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("drop"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("rotate"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("right"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("left"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("resume"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("exit"));
        keyListener.keyPressed(keyEvent);
    }

    @Test
    @DisplayName("키 입력 테스트-아이템")
    void itemKeyPressedTest() {
        setting.setGameMode(1);
        GamePage.GamePageKeyListener keyListener = new GamePage().new GamePageKeyListener();
        JButton jButtons = new JButton();
        KeyEvent keyEvent = new KeyEvent(jButtons, 0, 0, 1, setting.getKeyList().get("down"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("drop"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("rotate"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("right"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("left"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("resume"));
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(setting.getKeyList().get("exit"));
        keyListener.keyPressed(keyEvent);
    }
}