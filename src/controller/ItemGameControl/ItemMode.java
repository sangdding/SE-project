package controller.ItemGameControl;

import controller.block.ItemBlockController;

public interface ItemMode {
    public void spawnBlock2(int bln, int random, boolean item); //item == true 라면 random 8-12의 값을 가지며 랜덤 아이템 생성
                                                                //bln 은 아이템을 넣을 블럭의 번호, random==9라면 무게추 생성
    public boolean isBlockOuOofBounds();// 게임 종료 조건
    public void moveBlockDown();// 포커스 된 블럭을 아래로 내림
    public void moveBlockDown2();// 무게추 아이템이 포커스 블럭일 때에 아래로 움직임. 알아서 아이템 처리함
    public int[][] moveBlockRight();//오른쪽으로 블럭 옮김
    public int[][] moveBlockLeft();//왼쪽으로 블럭 옮김
    public boolean dropBlock();//블럭 떨구기
    public int[][] rotateBlock();// 블럭 회전.
    public void moveBlockToBackground();//블럭이 안착을 하면 백그라운드로 블럭을 보냄
    public int clearLines(); //13값의 행들을 없앰
    public int clearLines2(); //완성된 행을 13으로 바꿈(이펙트)+완성된 행의 수 리턴, 아이템들 처리
    public int[][] newBackground();//현재 블럭의 위치와 모양, 백그라운드를 합쳐서 리턴해줌
}
