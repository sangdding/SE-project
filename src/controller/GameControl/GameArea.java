package controller.GameControl;



import java.util.Random;
import controller.block.Block;
import controller.block.ItemBlockController;

public class GameArea {
    protected int gridRows; //행의 수
    protected int gridColumns; //열의 수
    protected int[][] background;
    public Block block;
    public GameArea() {//생성자
        gridColumns = 10;
        gridRows = 20;
        background = new int[gridRows][gridColumns];

    }
    public int[][] getBackground(){
        return background;
    }

}
