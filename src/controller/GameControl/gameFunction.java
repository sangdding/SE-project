package controller.GameControl;

public interface gameFunction {
    //bln = 블럭 종류 코드
    public void spawnBlock(int bln); //블럭 생성, 블럭 모델에 있는 0-6 정수의 인풋으로 블럭 번호
    public boolean isBlockOuOofBounds(); //게임 종료 여부
    /*****이 함수들은 background 리턴함.*********************/
    public void moveBlockDown(); //블럭 밑으로 움직
    public int[][] moveBlockRight(); //블럭 오른쪽 움직
    public int[][] moveBlockLeft(); //블럭 왼쪽
    public boolean dropBlock(); //블럭 한번에 떨구기
    public int[][] rotateBlock(); //블럭 회전
    /****************************************************/
    public int clearLines(); //행 제거 - 진짜 없애버림
    public int clearLines2(); //완성된 행을 12값으로 바꾸고(이펙트) 제거되는 행의 수를 리턴
    public void moveBlockToBackground(); //현재 블럭 백그라운드로 보내기
    public int[][] newBackground(); //현재 백그라운드와 포커스된 블럭을 그려넣은 2차원 배열을 리턴
}
