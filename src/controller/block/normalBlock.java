package controller.block;

public interface normalBlock {
    //일반 블럭 컨트롤러
    public void spawn(int gridWidth); //일반 블럭 생성, 게임 영역의 열의 수를 인풋으로 넣음
    public int[][] getShape(); //현재 블럭의 모양을 2차원 배열로 리턴
    public int[][] getNextShape();
    public int getColor();
    public int getWidth();
    public int getHeight();
    public int getX();
    public int getY();
    public void moveDown();
    public void moveRight();
    public void moveUp();
    public void moveLeft();
    public void rotate();
    public int getBottomEdge();
    public int getLeftEdge();
    public int getRightEdge();
}
