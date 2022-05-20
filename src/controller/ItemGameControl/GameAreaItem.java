package controller.ItemGameControl;
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

        /*for(int i = 0; i<9;i++)
        {
            background[19][i]=1;
        }
        for(int i = 0; i<9;i++)
        {
            background[18][i]=1;
        }
        for(int i = 0; i<9;i++)
        {
            background[17][i]=1;
        }*/
    }
    public int[][] getBackground(){
        return background;
    }


}
