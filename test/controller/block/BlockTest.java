package controller.block;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    private Block block;

    @BeforeEach
    void init() {
        block = new Block(0);
    }

    @Test
    @DisplayName("블록 생성 테스트")
    void spawn() throws NoSuchFieldException, IllegalAccessException {
        Field field = block.getClass().getDeclaredField("currentRotation");
        field.setAccessible(true);
        int[][] blockShape;
        for (int i = 0; i < 100; i++) {
            int currRotate = (int) field.get(block);
            block.spawn(10);
            block.rotate();
            blockShape = block.getShape();
            assertNotNull(blockShape); // 모양 잘 가져오는지
            blockShape = block.getNextShape();
            assertNotNull(blockShape); // 다음 모양 잘 가져 오는지
            assertTrue(currRotate >= 0 && currRotate < 4);
        }
    }
}