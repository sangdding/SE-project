package controller.block;

import model.block.NormalBlock;
import java.util.Random;

public class Block implements normalBlock{
    private int bln;
    public int[][] shape;
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
    @Override
    public void spawn(int gridWidth){
        Random r= new Random();
        currentRotation=r.nextInt(4);
        shape=inf.getBlockShape(bln,currentRotation);
        if(getHeight()>1){y= -getHeight()+1;}
        else{y=-getHeight();}
        x= r.nextInt(gridWidth-getWidth());
    }
    public void invertToItem(int r){
        Random r2 = new Random();
        int wid= this.shape[0].length;
        int hei = this.shape.length;
        int index =r2.nextInt(wid*hei);
        int Q = (int)index / wid ;
        do{
            if(this.shape[Q][index%wid]==1){
                this.shape[Q][index%wid]=r;
            }
        }while(this.shape[Q][index%wid]==0);
    }
    @Override
    public int[][] getShape(){return this.shape;} //지금 형태를 2차원 배열로 리턴
    @Override
    public int[][] getNextShape(){
        if(currentRotation==3){return inf.getBlockShape(bln,0);}
        return inf.getBlockShape(bln,currentRotation+1);
    }
    @Override
    public int getColor(){return this.color;} // 컬러 받기
    @Override
    public int getHeight(){return shape.length;}
    @Override
    public int getWidth(){return shape[0].length;}
    @Override
    public int getX(){ return x;}
    @Override
    public int getY(){ return y;}
    @Override
    public void moveDown(){y++;}
    @Override
    public void moveRight(){x++;}
    @Override
    public void moveUp(){y--;}
    @Override
    public void moveLeft(){x--;}
    @Override
    public void rotate(){
        currentRotation++;
        if(currentRotation >3) currentRotation = 0;
        shape=inf.getBlockShape(bln,currentRotation);
    } //회전
    @Override
    public int getBottomEdge(){
        return y+getHeight();
    } //아래끝 리턴
    @Override
    public int getLeftEdge(){ return x; } //왼쪽 끝 리턴
    @Override
    public int getRightEdge(){ return x+getWidth();} //오른쪽 끝 리턴
}
