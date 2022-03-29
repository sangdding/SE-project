package model;

import java.util.Random;

public class GameArea {

    private int gridRows;
    private int gridColumns;
    private int gridCellSize;
    private int[][] background;
    private TetrisBlock block;//블럭을 만드는 과정 L자 모형


    //여기가 거의 메인메소드
    public GameArea() {//생성자
        gridColumns = 10;
        gridRows = 20;
        background = new int[gridRows][gridColumns];
    }

    public boolean checkBottom() {
        if (block.getBottomEdge() == gridRows) {
            return false;
        }
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();
        for (int col = 0; col < w; col++) {
            for (int row = h - 1; row >= 0; row--) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX();
                    int y = row + block.getY() + 1;
                    if (y < 0) break;
                    if (background[y][x] != 0) return false;
                    break;
                }
            }
        }
        return true;
    }

    private boolean checkLeft() {
        if (block.getLeftEdge() == 0) return false;
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX() - 1;
                    int y = row + block.getY();
                    if (y < 0) break;
                    if (background[y][x] != 0) return false;
                    break;
                }
            }
        }
        return true;
    }

    private boolean checkRight() {
        if (block.getRightEdge() == gridColumns) return false;
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();
        for (int row = 0; row < h; row++) {
            for (int col = w - 1; col >= 0; col--) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX() - 1;
                    int y = row + block.getY();
                    if (y < 0) break;
                    if (background[y][x] != 0) return false;
                    break;
                }
            }
        }
        return true;
    }
    public void spawnBlock(int[][] shape, int color) { //블럭생성
        block = new TetrisBlock(shape, color);
        block.spawn(gridColumns);
    }

    public boolean isBlockOuOofBounds() {
        if (block.getY() < 0) { //맨 위 프레임을 건들여, 게임에서 진 상황
            block = null;
            return true;
        }
        return false;
    }
    public int[][] moveBlockDown() {
        block.moveDown();
        return background;
    }
    public int[][] moveBlockRight() {
        if (block == null) return background;
        if (!checkRight()) return background;
        block.moveRight();
        return background;
    }

    public int[][] moveBlockLeft() {
        if (block == null) return background;
        if (!checkLeft()) return background;
        block.moveLeft();
        return background;
    }
    public int[][] dropBlock() {
        if (block == null) return background;
        while (checkBottom()) {
            block.moveDown();
        }
        return background;
    }

    public int[][] rotateBlock() {
        if (block == null) return background;
        block.rotate();
        return background;
    }
    public void moveBlockToBackground() {
        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();
        int xPos = block.getX();
        int yPos = block.getY();
        int color = block.getColor();
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                if (shape[r][c] != 0) {
                    background[r + yPos][c + xPos] = color;
                }
            }
        }
    }


    //행제거
    public int clearLines() {
        boolean lineFilled;
        int linesCleared = 0;
        for (int r = gridRows - 1; r >= 0; r--) {
            lineFilled = true;
            for (int c = 0; c < gridColumns; c++) {
                if (background[r][c] == 0) {
                    lineFilled = false;
                }

            }
            if (lineFilled) {
                linesCleared++;
                clearLine(r);
                shiftDown(r);
                clearLine(0);
                r++; //한줄만 지워지는거 제외
            }

        }
        return linesCleared;
    }
    //행제거
    private void clearLine(int r) {

        for (int i = 0; i < gridColumns; i++) {
            background[r][i] = 0;
        }
    }
    //나머지 행들 끌어오기
    private void shiftDown(int r) {
        for (int row = r; row > 0; row--) {
            for (int col = 0; col < gridColumns; col++) {
                background[row][col] = background[row - 1][col];
            }
        }
    }
    public int[][] getBackground(){
        return background;
    }
}
