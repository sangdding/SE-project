package model.block;
import java.util.Random;
public class ItemBlock implements Block {

    private int[][][] normalBlock =
            {{{1}, {1}, {1}, {1}}, {{1, 0, 0}, {1, 1, 1}}, {{0, 0, 1}, {1, 1, 1}}, {{1, 1}, {1, 1}},
                    {{0, 1, 1}, {1, 1, 0}}, {{0, 1, 0}, {1, 1, 1}}, {{1, 1, 0}, {0, 1, 1}}};

    private int[][][][] rotateBlock = new int[7][4][][];

    private int[] color = {1, 2, 3, 4, 5, 6, 7};

    public ItemBlock(int random) {
        Random r2 = new Random();
        for(int i=0; i<7; i++){
        int wid= this.normalBlock[i][0].length;
        int hei = this.normalBlock[i].length;
        int index =r2.nextInt(wid*hei);
        int Q = (int)index / wid ;
        do{
            if(this.normalBlock[i][Q][index%wid]==1){
                this.normalBlock[i][Q][index%wid]=random;
            }
        }while(this.normalBlock[i][Q][index%wid]==0);}
        for (int i = 0; i < 7; i++) {
            int[][] tempBlock = normalBlock[i];
            for (int j = 0; j < 4; j++) {
                int r = tempBlock[0].length; //행
                int c = tempBlock.length;   //열
                rotateBlock[i][j] = new int[r][c];
                for (int y = 0; y < r; y++) {
                    for (int x = 0; x < c; x++) {
                        rotateBlock[i][j][y][x] = tempBlock[c - x - 1][y];
                    }
                }
                tempBlock = rotateBlock[i][j];
            }
        }
    }


    @Override
    public int[][][] getBlockList() {
        return normalBlock;
    }

    @Override
    public int[][] getBlockShape(int index, int rotateNum) {
        return rotateBlock[index][rotateNum];
    }

    @Override
    public int getColor(int index) {
        return color[index];
    }
}