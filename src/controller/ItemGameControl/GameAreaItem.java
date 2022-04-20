package controller.ItemGameControl;
import java.util.Random;
import controller.block.ItemBlockController;
public class GameAreaItem {
    protected int gridRows; //행의 수
    protected int gridColumns; //열의 수
    protected int[][] background;
    public ItemBlockController block;
    public GameAreaItem() {//생성자
        gridColumns = 10;
        gridRows = 20;
        background = new int[gridRows][gridColumns];
    }
    public int[][] getBackground(){
        return background;
    }


}
