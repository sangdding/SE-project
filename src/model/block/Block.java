package model.block;

public interface Block {

    int[][][] getBlockList(); // 회전 안한 기본 블록 모양을 가져온다.

    int[][] getBlockShape(int index, int rotateNum); // 특정 블록 모양이 rotateNum 만큼 회전 했을때 모양을 가져온다.

    int getColor(int index); // 특정 블록 모양의 컬러값을 int로 반환한다.
}
