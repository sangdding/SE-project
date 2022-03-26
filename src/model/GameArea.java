
package model;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GameArea extends JPanel{
    
    private int gridRows;
    private int gridColumns;
    private int gridCellSize;
    private Color[][] background;
    
    private TetrisBlock block;//블럭을 만드는 과정 L자 모형
    
    
    //여기가 거의 메인메소드
    public GameArea(JPanel placeholder,int columns){//생성자
        placeholder.setVisible(false);
        this.setBounds(placeholder.getBounds()); //왼쪽 대각선 좌표, 오른쪽 대각선 좌표
        //this.setBackground(Color.RED); //배경 색 설정
        this.setBackground(placeholder.getBackground()); //객체의 배경색을 알아서 받아옴
        this.setBorder(placeholder.getBorder());
        gridColumns= columns;
        gridCellSize=this.getBounds().width/ columns;
        gridRows =this.getBounds().height / gridCellSize;
        background = new Color[gridRows][gridColumns];
    } 
    private boolean checkBottom(){
        if(block.getBottomEdge()== gridRows){
            return false;
        }
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h= block.getHeight();
        for(int col=0; col<w; col++){
            for(int row=h-1; row>=0; row--)
            {
                if(shape[row][col]!=0){
                    int x = col+block.getX();
                    int y = row+block.getY()+1;
                    if(y<0) break;
                    if(background[y][x]!=null) return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkLeft(){
        if(block.getLeftEdge()==0) return false;
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h= block.getHeight();
        for(int row=0; row<h; row++){
            for(int col=0; col<w; col++)
            {
                if(shape[row][col]!=0){
                    int x = col+block.getX() -1;
                    int y = row+block.getY();
                    if(y<0) break;
                    if(background[y][x]!=null) return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkRight(){
    if(block.getRightEdge()==gridColumns) return false;
    int[][] shape = block.getShape();
    int w = block.getWidth();
    int h= block.getHeight();
    for(int row=0; row<h; row++){
        for(int col=w-1; col>= 0; col--)
        {
            if(shape[row][col]!=0){
                int x = col+block.getX() -1;
                int y = row+block.getY();
                if(y<0) break;
                if(background[y][x]!=null) return false;
                break;
            }
        }
    }
        return true;
    }

    public void spawnBlock(int[][] shape,Color color)
    { //블럭생성
        block = new TetrisBlock( shape, color);
        block.spawn(gridColumns);
    }
    public boolean isBlockOuOofBounds(){
        if(block.getY() <0 ){
            block=null;
            return true;
        }
        return false;
    }
    public  boolean moveBlockDown(){

        if(checkBottom()==false){//바닥에 닿으면 지금 템프의 블럭을 백그라운드로 옮김.

           return false;
        }
        block.moveDown();
        repaint();
        return true;
    }
    public void moveBlockRight(){
        if(block == null) return;
        if(!checkRight()) return;
        block.moveRight();
        repaint();
    }
    public void moveBlockLeft(){
        if(block == null) return;
        if(!checkLeft()) return;
        block.moveLeft();
        repaint();
    }
    public void dropBlock(){
        if(block == null) return;
        while(checkBottom()){
        block.moveDown();}
    }
    public void rotateBlock(){
        if(block == null) return;
        block.rotate();
        repaint();
    }
    public void moveBlockToBackground(){
        int[][] shape = block.getShape();
        int h= block.getHeight();
        int w=block.getWidth();
        int xPos= block.getX();
        int yPos= block.getY();
        Color color = block.getColor();
        for (int r=0; r<h; r++){
            for (int c=0; c<w; c++){
                if(shape[r][c]==1){
                    background[r+yPos][c+xPos] = color;
                }
            }
        }
    }
    //블럭을 만들기 
    private void drawBlock(Graphics g){
        int h= block.getHeight();
        int w = block.getWidth();
        Color c = block.getColor();
        int[][] shape = block.getShape();
        for(int row=0; row<h;row++){
            for ( int col=0; col<w;col++){
                if(shape[row][col] == 1)
                {
                    int x= (block.getX()+col)*gridCellSize;
                    int y=(block.getY()+row)*gridCellSize;
                    drawGridSquare(g,c,x,y);
                }
            }
        }
    }
    public int clearLines(){
        boolean lineFilled;
        int linesCleared = 0 ; 
        for( int r= gridRows -1 ; r>=0 ; r--){
            lineFilled = true;
            for(int c=0; c<gridColumns; c++)
            {
                if(background[r][c] == null)
                {
                    lineFilled = false;
                    break;
                }
            }
            if(lineFilled){
                linesCleared++;
                clearLine(r);
                shiftDown(r);
                clearLine(0);
                r++; //한줄만 지워지는거 제외
                repaint();
            }
        }
        return linesCleared;
    }
    private void clearLine(int r){

         for(int i=0; i<gridColumns;i++)
                {
                    background[r][i]=null;
                }
    }
    private void shiftDown(int r){
        for(int row=r; row>0; row--){
            for(int col=0; col<gridColumns;col++)
            {
                background[row][col]=background[row-1][col];
            }
        }
    }
    private void drawBackGround(Graphics g){//처리가 끝난 블럭을 처리하는 방법
        Color color;
        for(int r=0; r<gridRows; r++){
            for(int c =0; c<gridColumns; c++){
                color=background[r][c]; //null = no color
                if(color !=null){
                    int x=c*gridCellSize;
                    int y=r*gridCellSize;
                    drawGridSquare(g,color,x,y);
                }
            }
        }
    }
    private void drawGridSquare(Graphics g,Color color ,int x, int y){
        g.setColor(color);
        g.fillRect(x, y, gridCellSize, gridCellSize);
        g.setColor(Color.black);
        g.drawRect(x,y,gridCellSize,gridCellSize);
    }
    
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g); //게임영역 원하는 색으로 채우기 위해서 필요함
        for(int x=0; x<gridRows; x++){
            g.drawRect(0, x*gridCellSize, gridCellSize, gridCellSize);
        }
        //d이게 그 배경에 그리드 칠하는거. 
        for(int x=0; x<gridColumns; x++){
            for(int j=0; j<gridRows; j++){
            g.drawRect(x*gridCellSize, j*gridCellSize, gridCellSize, gridCellSize);
            }
            
        }
        drawBackGround(g); //백그라운드
        drawBlock(g);
    }
    
}
