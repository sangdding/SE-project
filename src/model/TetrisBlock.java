package model;
import java.util.Random;

public class TetrisBlock {
    private int[][] shape;
    private int color;
    private int x,y;
    private int[][][] shapes;
    private int currentRotation;
    public TetrisBlock(int[][] shape, int color)
    {
        this.shape = shape;
        this.color = color; //블럭 생성자
        initShapes(); //모양 생성

    }
    private void initShapes(){
        shapes=new int[4][][];
        for(int i=0; i<4; i++){
            int r= shape[0].length;
            int c= shape.length;
            shapes[i] = new int[r][c];
            for(int y=0; y<r; y++){
                for(int x=0;x<c;x++){
                    shapes[i][y][x] = shape[c-x-1][y];
                }
            }
            shape=shapes[i];
        }
    }
    public void spawn(int gridWidth){
        Random r= new Random();
        currentRotation=r.nextInt(shapes.length);
        currentRotation = shapes.length;
        shape=shapes[currentRotation];
        y= -getHeight();
        x= r.nextInt(gridWidth-getWidth());
    }
    public int[][] getShape(){return this.shape;} //지금 형태를 2차원 배열로 리턴
    public int getColor(){return this.color;} // 컬러 받기
    public int getHeight(){return shape.length;}
    public int getWidth(){return shape[0].length;}
    public int getX(){ return x;}
    public int getY(){ return y;}
    public void moveDown(){y++;}
    public void moveRight(){x++;}
    public void moveLeft(){x--;}

    public void rotate(){
        currentRotation++;
        if(currentRotation >3) currentRotation = 0;
        shape=shapes[currentRotation];
    } //회전
    public int getBottomEdge(){
        return y+getHeight();
    } //아래끝 리턴
    public int getLeftEdge(){ return x; } //왼쪽 끝 리턴
    public int getRightEdge(){ return x+getWidth();} //오른쪽 끝 리턴
}