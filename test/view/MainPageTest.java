package view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ENTER;
import static org.junit.jupiter.api.Assertions.*;

class MainPageTest {

    @Test
    @DisplayName("키 입력 테스트")
    void keyPressedTest() throws NoSuchFieldException {
        MainPage.MainPageKeyListener keyListener = new MainPage().new MainPageKeyListener();
        JButton jButtons = new JButton();
        KeyEvent keyEvent = new KeyEvent(jButtons, 0, 0, 1, VK_DOWN);
        keyListener.keyPressed(keyEvent);
        keyEvent.setKeyCode(VK_ENTER);
        keyListener.keyPressed(keyEvent);
    }

}