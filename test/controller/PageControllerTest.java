package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageControllerTest {

    PageController pageController;

    @Test
    void testPageController() {
        pageController = new PageController("Setting");
        pageController = new PageController("Main");
        pageController = new PageController("ScoreBoard - Normal");
        pageController = new PageController("ScoreBoard - Item");
        pageController = new PageController("Key Setting");
        pageController = new PageController("Game Start - Normal Mode");
        pageController = new PageController("Game End");
        assertTrue(true);
    }
}