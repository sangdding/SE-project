package model.block;

import java.util.ArrayList;
import java.util.Random;

public class ItemBlock implements Block {

    private int[][][] normalBlock =
            {{{1}, {1}, {1}, {1}}, {{1, 0, 0}, {1, 1, 1}}, {{0, 0, 1}, {1, 1, 1}}, {{1, 1}, {1, 1}},
                    {{0, 1, 1}, {1, 1, 0}}, {{0, 1, 0}, {1, 1, 1}}, {{1, 1, 0}, {0, 1, 1}}};

    private int[][][] itemBlock = {{{1}}, {{0, 1, 1, 0}, {1, 1, 1, 1}}, {{0}}, {{0, 1, 0}, {1, 1, 1}, {0, 1, 0}}, {}};
    private final int[][][][] rotateItemBlock = new int[3][4][][];

    private final int[] color = {8, 9, 10, 11, 12};

    private Random random = new Random();

    public ItemBlock() {
        int select = random.nextInt(7);
        itemBlock[2] = itemGenerator(normalBlock[select]);

        for (int i = 0; i < 3; i++) {
            int[][] tempBlock = itemBlock[i];
            for (int j = 0; j < 4; j++) {
                int r = tempBlock[0].length;
                int c = tempBlock.length;
                rotateItemBlock[i][j] = new int[r][c];
                for (int y = 0; y < r; y++) {
                    for (int x = 0; x < c; x++) {
                        rotateItemBlock[i][j][y][x] = tempBlock[c - x - 1][y];
                    }
                }
                tempBlock = rotateItemBlock[i][j];
            }
        }
    }

    private int[][] itemGenerator(int[][] blockInfo) {

        ArrayList<int[]> index = new ArrayList<>();
        int[] temp = new int[2];

        for (int i = 0; i < blockInfo.length; i++) {
            for (int j = 0; j < blockInfo[i].length; j++) {
                if(blockInfo[i][j] == 1) {
                    temp[0] = i;
                    temp[1] = j;
                    index.add(temp);
                }
            }
        }

        int select = random.nextInt(index.size()-1);
        temp = index.get(select);
        blockInfo[temp[0]][temp[1]] = 2;

        return blockInfo;
    }

    @Override
    public int[][][] getBlockList() {
        return itemBlock;
    }

    @Override
    public int[][] getBlockShape(int index, int rotateNum) {
        return rotateItemBlock[index][rotateNum];
    }

    @Override
    public int getColor(int index) {
        return color[index];
    }
}
