package controller.block;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    private Block block;
    private ItemBlockController itemBlockController;
    private Random random = new Random();

    @RepeatedTest(20)
    @DisplayName("블록 생성 테스트")
    void spawn() throws NoSuchFieldException, IllegalAccessException {
        Block block = new Block(random.nextInt(4));
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

    @RepeatedTest(20)
    @DisplayName("아이템블록 생성 테스트")
    void itemSpawn() throws NoSuchFieldException, IllegalAccessException {
        ItemBlockController itemBlockController = new ItemBlockController(0, random.nextInt(8), true);
        Field field = itemBlockController.getClass().getDeclaredField("currentRotation");
        field.setAccessible(true);
        int[][] blockShape;
        for (int i = 0; i < 100; i++) {
            int currRotate = (int) field.get(itemBlockController);
            itemBlockController.spawn(10);
            itemBlockController.rotate();
            blockShape = itemBlockController.getShape();
            assertNotNull(blockShape); // 모양 잘 가져오는지
            blockShape = itemBlockController.getNextShape();
            assertNotNull(blockShape); // 다음 모양 잘 가져 오는지
            itemBlockController.doser(10);
            assertTrue(currRotate >= 0 && currRotate < 4);
        }
    }
}