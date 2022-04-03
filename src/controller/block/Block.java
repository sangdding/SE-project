package controller.block;

import model.block.NormalBlock;

import java.util.Random;

public class Block {
    private int bln;
    private int[][] shape;
    private int color;
    private int x,y;
    private int[][][] shapes;
    private int currentRotation=0;
    private NormalBlock inf;
    public Block(int bln){
        this.bln=bln;
        NormalBlock ca = new NormalBlock();
        this.inf =ca;
        this.shape=inf.getBlockShape(bln,currentRotation);
        this.color=inf.getColor(bln);
    }
    public void spawn(int gridWidth){
        Random r= new Random();
        currentRotation=r.nextInt(4);
        shape=inf.getBlockShape(bln,currentRotation);
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
        shape=inf.getBlockShape(bln,currentRotation);
    } //회전
    public int getBottomEdge(){
        return y+getHeight();
    } //아래끝 리턴
    public int getLeftEdge(){ return x; } //왼쪽 끝 리턴
    public int getRightEdge(){ return x+getWidth();} //오른쪽 끝 리턴
}
