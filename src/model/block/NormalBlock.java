package model.block;

public class NormalBlock {

    private int[][][] block =
            {{{1},{1},{1},{1}},{{1,0,0},{1,1,1}},{{0,0,1},{1,1,1}},{{1,1},{1,1}},
    {{0,1,1},{1,1,0}},{{0,1,0},{1,1,1}},{{1,1,0},{0,1,1}}};

    private int[] color = {1, 2, 3, 4, 5, 6, 7};

    public int[][][] getBlock() {
        return block;
    }

    public int[] getColor() {
        return color;
    }
}