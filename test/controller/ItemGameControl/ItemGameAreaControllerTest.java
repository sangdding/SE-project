package controller.ItemGameControl;

import controller.GameControl.GameAreaController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.GamePage;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ItemGameAreaControllerTest {
    private GamePage gamePage = new GamePage();
    private ItemGameAreaController gameAreaController = new ItemGameAreaController(gamePage);
    private int[][] forTest = new int[][]{
            {0, 0, 0, 0, 7, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0},
            {1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    @BeforeEach
    void drawBackground() throws NoSuchFieldException, IllegalAccessException {
        gameAreaController.ga.background = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
    }

    @Test
    @DisplayName("블록 좌우 하단 체크")
    void checkSide() throws NoSuchFieldException, IllegalAccessException {
        gameAreaController.spawnBlock2(0, 9, true);
        gameAreaController.ga.block.rotate();
        Field x = gameAreaController.ga.block.getClass().getDeclaredField("x");
        Field y = gameAreaController.ga.block.getClass().getDeclaredField("y");
        x.setAccessible(true);
        y.setAccessible(true);
        x.setInt(gameAreaController.ga.block, 10);
        y.setInt(gameAreaController.ga.block, 0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> gameAreaController.moveBlockDown());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> gameAreaController.moveBlockDown2());
        int i;
        gameAreaController.rotateBlock();
        for (i = 0; i < gameAreaController.ga.background.length; i++) {
            if (!gameAreaController.checkBottom()) {
                Assertions.assertEquals(14, i);
                break;
            }
            gameAreaController.ga.block.moveDown();
        }

    }

    @Test
    @DisplayName("라인 클리어")
    void clearLine() {
        Assertions.assertEquals(1, gameAreaController.clearLines2());
        Assertions.assertEquals(1, gameAreaController.clearLines());
    }

    @Test
    @DisplayName("새로운 배경 출력")
    void getNewBackground() throws NoSuchFieldException, IllegalAccessException {
        gameAreaController.spawnBlock2(0, 12, true);
        Field x = gameAreaController.ga.block.getClass().getDeclaredField("x");
        Field y = gameAreaController.ga.block.getClass().getDeclaredField("y");
        x.setAccessible(true);
        y.setAccessible(true);
        x.setInt(gameAreaController.ga.block, 4);
        y.setInt(gameAreaController.ga.block, 0);
        gameAreaController.rotateBlock();
        gameAreaController.moveBlockLeft();
        gameAreaController.moveBlockRight();
        gameAreaController.moveBlockToBackground();
        gameAreaController.dropBlock();
        int[][] newBackground = gameAreaController.newBackground();
        Assertions.assertFalse(gameAreaController.isBlockOuOofBounds());
    }

    @Test
    @DisplayName("위에 닿았는지 체크")
    void checkUp() throws NoSuchFieldException, IllegalAccessException {
        gameAreaController.spawnBlock2(0, 4, true);
        Field x = gameAreaController.ga.block.getClass().getDeclaredField("x");
        Field y = gameAreaController.ga.block.getClass().getDeclaredField("y");
        x.setAccessible(true);
        y.setAccessible(true);
        x.setInt(gameAreaController.ga.block, 4);
        y.setInt(gameAreaController.ga.block, -1);
        assertTrue(gameAreaController.isBlockOuOofBounds());
    }
}