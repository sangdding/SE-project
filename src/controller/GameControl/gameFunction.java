package controller.GameControl;

public interface gameFunction {
    //bln = 블럭 종류 코드
    public void spawnBlock(int bln); //블럭 생성
    public boolean isBlockOuOofBounds(); //게임 종료 여부
    /*****이 함수들은 background 리턴함.*********************/
    public int[][] moveBlockDown(); //블럭 밑으로 움직
    public int[][] moveBlockRight(); //블럭 오른쪽 움직
    public int[][] moveBlockLeft(); //블럭 왼쪽
    public int[][] dropBlock(); //블럭 한번에 떨구기
    public int[][] rotateBlock(); //블럭 회전
    /****************************************************/
    public int clearLines(); //행 제거
    public void moveBlockToBackground(); //현재 블럭 백그라운드로 보내기

}
