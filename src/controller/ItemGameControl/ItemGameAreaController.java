package controller.ItemGameControl;
import controller.block.ItemBlockController;
import view.GamePage;
import view.temp;

public class ItemGameAreaController extends GameAreaItem implements ItemMode {
    public GameAreaItem ga;
    public temp tp;
    public int i=0;
    public ItemBlockController nextBlock;
    public int nextRandom;
    public ItemGameAreaController(temp tp){
        this.ga=new GameAreaItem();
        this.tp=tp;
        this.nextBlock=null;
        this.nextRandom=1;
    }

    // 커밋
    public boolean checkBottom() {
        if (ga.block == null) {
            return false;
        } else {
            if (ga.block.getBottomEdge() == ga.gridRows) {
                return false;
            }
            int[][] shape = ga.block.getShape();
            int w = ga.block.getWidth();
            int h = ga.block.getHeight();
            for (int row = h - 1; row >= 0; row--) {
                for (int col = 0; col < w; col++) {
                    if (shape[row][col] != 0) {
                        int x = col + ga.block.getX();
                        int y = row + ga.block.getY() + 1;
                        if (y < 0) break;
                        if (y >= 20) {
                            return false;
                        }
                        if (ga.background[y][x] != 0) return false;
                    }
                }
            }
            return true;
        }
    }
    private boolean checkLeft() {
            if (ga.block.getLeftEdge() == 0) return false;
            int[][] shape = ga.block.getShape();
            int w = ga.block.getWidth();
            int h = ga.block.getHeight();
            for (int row = 0; row < h; row++) {
                for (int col = 0; col < w; col++) {
                    if (shape[row][col] != 0) {
                        int x = col + ga.block.getX() - 1;
                        int y = row + ga.block.getY();
                        if (y < 0) break;
                        if (ga.background[y][x] != 0) return false;
                        break;
                    }
                }
            }
            return true;
        }

    private boolean checkRight() {
        if (ga.block.getRightEdge() == ga.gridColumns) return false;
        int[][] shape = ga.block.getShape();
        int x = ga.block.getX();
        int y = ga.block.getY();
        if(ga.block.getRightEdge()==10){return false;}
        for(int r=y; r<y+ga.block.getHeight();r++){
            for(int c=x; c<x+ga.block.getWidth();c++){
                if(y<0){
                    if(r<0){break;}
                    else {
                        if (ga.block.getShape()[r - y - 1][c - x] != 0) {
                            if (ga.getBackground()[r][c+1]!=0){return false;}
                        }
                    }
                }
                else{
                    if(ga.block.getShape()[r-y][c-x]!=0)
                    {
                        if(ga.getBackground()[r][c+1]!=0){return false;}
                    }
                }
            }
        }
        return true;
    }
    public void spawnBlock(int bln, int random, boolean item){
        ga.block = new ItemBlockController(bln,random,item);
        ga.block.spawn(gridColumns);
    }
    @Override
    public void spawnBlock2(int bln, int random,boolean item){
        if(random==9){//불도저 만들기
            this.nextBlock = new ItemBlockController(8,random,item);
            this.nextRandom=random;
        }
        else {
            if(random==12){
                this.nextBlock = new ItemBlockController(7,12,item);
                this.nextRandom=random;
            }
            else {
                this.nextBlock = new ItemBlockController(bln, random, item);
                this.nextRandom=random;
            }
        }
    }
    public void switchBlock(){
        if(nextRandom==9){//불도저 만들기
            ga.block = nextBlock;
            ga.block.doser(gridColumns);
        }
        else {
            if(nextRandom==12){
                ga.block = nextBlock;
                ga.block.spawn(gridColumns);
            }
            else {
                ga.block = nextBlock;
                ga.block.spawn(gridColumns);
            }
        }
    }
    @Override
    public boolean isBlockOuOofBounds() {
        if (ga.block.getY() < 0) { //맨 위 프레임을 건들여, 게임에서 진 상황
            ga.block = null;
            return true;
        }
        return false;
    }
    @Override
    public void moveBlockDown() {
        if(ga.block==null){}
        if(ga.block.shape[0][0]==9){
            //불도저아이템인 경우
            int y = ga.block.getY();
            if(y<0){ga.block.moveDown();}
            else{
                if(y+1!=gridRows){
                    int x=ga.block.getX();
                    for(int c=x; c<x+4;c++){
                        ga.background[y+1][c]=0;
                    }
                    ga.block.moveDown();

                }
                else{
                    ga.block=null;
                }
            }
        }
        if(!checkBottom()){}
        else{ga.block.moveDown();
            tp.score++;}
    }
    @Override
    public void moveBlockDown2(){
        if(ga.block==null){}
        else {
            int y = ga.block.getY();
            int x = ga.block.getX();
            if (y == 19) {
                tp.end=true;
            } else {
                for (int c = x; c < x + 4; c++) {
                    if (ga.background[y + 1][c] != 0) {
                        tp.chew = true;
                    }
                    if (ga.background[y + 1][c] <= 7) {
                        ga.background[y + 1][c] = 0;
                    } else {
                        if (ga.background[y + 1][c] == 10) {
                            tp.delay += (int) (300 / tp.velocity);
                            ga.background[y + 1][c] = 0;
                        }
                        if (ga.background[y + 1][c] == 11) {
                            tp.doubleIndex += 10;
                            ga.background[y + 1][c] = 0;
                        }
                    }
                }
                ga.block.moveDown();
                tp.score++;
            }
        }
    }
    public ItemBlockController getNextBlock(){
        return this.nextBlock;
    }
    @Override
    public int[][] moveBlockRight() {
        if (ga.block == null) return ga.background;
        if (!checkRight()) return ga.background;
        ga.block.moveRight();
        return ga.background;
    }
    @Override
    public int[][] moveBlockLeft() {
        if (ga.block == null) return ga.background;
        if (!checkLeft()) return ga.background;
        ga.block.moveLeft();
        return ga.background;
    }
    @Override
    public boolean dropBlock() {
        if(ga.block == null){return false;}
        else{
            int i=0;
            while (checkBottom()) {
                i++;
            ga.block.moveDown();
        }
            tp.score+=i;}
        return true;
    }
    @Override
    public int[][] rotateBlock(){
        if (ga.block == null) return ga.background;
        int[][] nextShape=ga.block.getNextShape();
        ga.block.rotate();
        if(ga.block.getX()+ga.block.getWidth()-1>=10){
            if(checkLeft())
            {
                while(ga.block.getX()+ga.block.getWidth()-1>=10)
                {ga.block.moveLeft();}
            }
            else{
                for(int i=0 ;i<3;i++){
                    ga.block.rotate();}
            if(!checkBottom()){for(int i=0 ;i<3;i++){
                ga.block.rotate();}}
            }
        }
        if(!checkBottom()){
            while(!checkBottom()){
                ga.block.moveUp();
            }
        }
        return ga.background;
    }
    @Override
    public void moveBlockToBackground(){
        int[][] shape = ga.block.getShape();
        int h = ga.block.getHeight();
        int w = ga.block.getWidth();
        int xPos = ga.block.getX();
        int yPos = ga.block.getY();
        int color = ga.block.getColor();
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                if (shape[r][c] != 0) {
                    if(shape[r][c]==1){
                        ga.background[r + yPos][c + xPos] = color;
                    }
                    else if(shape[r][c]==9){
                        ga.background[r+yPos][c+xPos]=0;
                    }
                    else{
                        ga.background[r+yPos][c+xPos] = shape[r][c];
                    }
                }
            }
        }
    }

    //행제거
    @Override
    public int clearLines() {
        boolean lineFilled;
        boolean Line=false;
        boolean Time=false;
        int Sco=0;
        boolean Sco2=false;
        int linesCleared = 0;
        for (int r = ga.gridRows - 1; r >= 0; r--) {
            lineFilled = true;
            for (int c = 0; c < ga.gridColumns; c++) {
                if(ga.background[r][c]==0){lineFilled=false;}
                if(ga.background[r][c]==8){Line=true;}
                if(ga.background[r][c]==10){Time=true;;}
                if(ga.background[r][c]==11){Sco++;}
            }
            if(Line){lineFilled=true;}
            if(ga.background[r][0]==13){lineFilled=true;}
            if(lineFilled){ //Line 아이템이 있거나 라인이 다 차있는 경우
                Line=false;
                linesCleared++;
                clearLine(r);
                shiftDown(r);
                clearLine(0);
                r++; //한줄만 지워지는거 제외
                if(Time){tp.delay+=300*tp.velocity;}
                if(Sco>0){for(int i=0; i<Sco;i++){tp.doubleIndex+=10;}}
            }
            else{
                Time=false;Sco=0;
            }
        }
        return linesCleared;
    }
    @Override
    public int clearLines2() {
        boolean lineFilled;
        boolean Line=false;
        boolean Time=false;
        int Sco=0;
        boolean Sco2=false;
        int linesCleared = 0;
        for (int r = ga.gridRows - 1; r >= 0; r--) {
            lineFilled = true;
            for (int c = 0; c < ga.gridColumns; c++) {
                if(ga.background[r][c]==0){lineFilled=false;}
                if(ga.background[r][c]==8){Line=true;}
                if(ga.background[r][c]==10){Time=true;;}
                if(ga.background[r][c]==11){Sco++;}
            }
            if(Line){lineFilled=true;}
            if(lineFilled){ //Line 아이템이 있거나 라인이 다 차있는 경우
                Line=false;
                linesCleared++;
                clearLine2(r);
                if(Time){tp.delay+=300*tp.velocity;}
                if(Sco>0){for(int i=0; i<Sco;i++){tp.doubleIndex+=10;}}
            }
            else{
                Time=false;Sco=0;
            }
        }
        return linesCleared;
    }
    private void clearLine2(int r) {
        for (int i = 0; i < ga.gridColumns; i++) {
            ga.background[r][i] = 13;
        }
    }
    //행제거
    private void clearLine(int r) {
        for (int i = 0; i < ga.gridColumns; i++) {
            ga.background[r][i] = 0;
        }
    }
    //나머지 행들 끌어오기
    private void shiftDown(int r) {
        for (int row = r; row > 0; row--) {
            for (int col = 0; col < ga.gridColumns; col++) {
                ga.background[row][col] = ga.background[row - 1][col];
            }
        }
    }

    public int[][] getBackground(){
        return ga.background;
    }
    @Override
    public int[][] newBackground(){
        if(ga.block == null){return ga.background;}
        int[][] newBackground = new int[gridRows][gridColumns];
        for(int i=0; i<20; i++){
            for(int j=0; j<10; j++){
                newBackground[i][j]=ga.getBackground()[i][j];
            }
        }
        int x=ga.block.getX();
        int y=ga.block.getY();
        for(int r=y; r<y+ga.block.getHeight() ; r++){
            for(int c=x; c<x+ga.block.getWidth(); c++){
                if(y<0){
                    if(r<0){
                        break;
                    }
                    else{
                        if(ga.block.getShape()[r-y][c-x]==1){
                            newBackground[r][c]=ga.block.getColor();
                        }
                        else if(ga.block.getShape()[r-y][c-x]>1){
                            newBackground[r][c]=ga.block.getShape()[r-y][c-x];
                        }
                    }
                }
                else{
                    if(ga.block.getShape()[r-y][c-x]==1){
                        newBackground[r][c]=ga.block.getColor();
                    }
                    else if(ga.block.getShape()[r-y][c-x]>1){
                        newBackground[r][c]=ga.block.getShape()[r-y][c-x];
                    }
                }

            }
        }
        return newBackground;
    }
    public int[][] rowsToSend(){
        if(ga.block == null){return null;}
        int[][] newBackground = new int[gridRows][gridColumns];
        for(int i=0; i<20; i++){
            for(int j=0; j<10; j++){
                newBackground[i][j]=ga.getBackground()[i][j];
            }
        }
        int x=ga.block.getX();
        int y=ga.block.getY();
        for(int r=y; r<y+ga.block.getHeight() ; r++){
            for(int c=x; c<x+ga.block.getWidth(); c++){
                if(y<0){
                    if(r<0){
                        break;
                    }
                    else{
                        if(ga.block.getShape()[r-y][c-x]==1){
                            newBackground[r][c]=20;
                        }
                        else if(ga.block.getShape()[r-y][c-x]>1){
                            newBackground[r][c]=20;
                        }
                    }
                }
                else{
                    if(ga.block.getShape()[r-y][c-x]==1){
                        newBackground[r][c]=20;
                    }
                    else if(ga.block.getShape()[r-y][c-x]>1){
                        newBackground[r][c]=20;
                    }
                }
            }
        }
        int[] array = new int[20];
        for(int i=0;i<array.length;i++){
            array[i]=0;
        }
        for(int r=19;r>=0;r--){
            boolean lineFilled=true;
            for(int c=0; c<10;c++){
                if(newBackground[r][c]==0){
                    lineFilled=false;
                }
            }
            if(lineFilled){array[r]=1;}
        }
        int sum=0;
        for(int i=0;i<20;i++){
            if(array[i]==1){
                sum++;
            }
        }
        int[][] toSend = new int[sum][10];
        sum=0;
        for(int i=array.length-1;i>=0;i--){
            if(array[i]==1){
                for(int j=0; j<10;j++){
                    if(newBackground[i][j]!=20){
                        toSend[sum][j]=2;
                    }
                    else{
                        toSend[sum][j]=0;
                    }
                }
                sum++;
            }
        }
        if(sum>1){
            return toSend;
        }
        else{
            return null;
        }
    }
    public boolean receive(int[][] arr){
        for(int k=0; k<arr.length;k++) {
            for (int r = 1; r < 20; r++) {
                for (int c = 0; c < 10; c++) {
                    if (r < arr.length) {
                        if (ga.background[r][c] != 0) {
                            return false;
                        }
                    }
                }
                if (ga.block.getY() != -ga.block.getHeight() + 1) {
                } else {
                    ga.block.moveUp();
                }
                for (int c = 0; c < 10; c++) {
                    ga.background[r - 1][c] = ga.background[r][c];
                }
                //여기서 올릴 때 확인을 해야함..
            }
        }
        int i=0;int r=19;
        while(true){
            for(int c=0; c<10; c++){
                ga.background[r][c]=arr[i][c];
            }
            if(i==arr.length-1){break;}
            else{i++;r--;}
        }
        return true;
    }

}
