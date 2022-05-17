package view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class MainPageTest {

    @Test
    @DisplayName("키 입력 테스트")
    void keyPressedTest() throws NoSuchFieldException {
        MainPage mainPage = new MainPage();
        Field temp = MainPage.class.getDeclaredField("setKeyEventController");
        temp.setAccessible(true);
        temp.keyP
    }

}