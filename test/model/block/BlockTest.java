package model.block;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    Block block = new NormalBlock();

    @Test
    void 블럭_모양_출력_테스트() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                int[][] temp = block.getBlockShape(i, j);
                for (int x = 0; x < temp.length; x++) {
                    for (int y = 0; y < temp[x].length; y++) {
                        System.out.print(temp[x][y]);
                    }
                    System.out.println();
                }
                System.out.println();
            }
            System.out.println("==========================");
        }
    }

}