package view;

import controller.ItemGameControl.ItemGameAreaController;
import controller.block.ItemBlockController;
import model.Generator;
import model.block.NormalBlock;
import model.setting.JsonSetting;
import model.setting.Setting;

import java.util.Random;

public class temp {
    private Setting setting = new JsonSetting();
    private Random r = new Random();
    private Random r2 = new Random();
    private Generator gen;
    public ItemGameAreaController itemGameAreaController;
    public boolean doubleScore; //아이템 변수
    public int doubleIndex;
    public boolean fifth; //아이템 변수
    public double delay; //아이템 변수
    public double velocity;
    private boolean Effect;


    public int score;
    private int lineIndex;
    private int next;
    public int lines;
    public boolean chew;
    private NormalBlock BlockModel = new NormalBlock();
    public boolean end;

    temp()
    {
        itemGameAreaController = new ItemGameAreaController(this);



        //난이도별 생성기 세팅
        this.gen = new Generator(setting.getDifficulty());
        this.next = 1;

        //맨 처음 타임유닛, 점수 설정

        this.doubleScore = false;
        this.doubleIndex = 0;
        this.lines = 0;
        this.chew = false;
        this.end=false;
        switch (setting.getDifficulty()) {
            case 0://normal
                this.velocity = 1;
                break;
            case 1://easy
                this.velocity = 0.8;
                break;
            case 2:
                this.velocity = 1.2;
                break;
            default:
                break;
        }
        this.delay = 1000 / velocity;
        this.lineIndex = 0;
        this.Effect = false;
        itemGameAreaController.spawnBlock(gen.getArr()[0],1,false);
        itemGameAreaController.spawnBlock2(gen.getArr()[1],1,false);
        this.next=2;
    }

    public int[][] getBackground()
    {
        return itemGameAreaController.getBackground();
    }

    public int getScore()
    {
        return score;
    }

    public double getDelay()
    {
        return delay;
    }

    public int getDoubleIndex()
    {
        return doubleIndex;
    }

    public void clearLinesInTempClass()
    {
        if (Effect) {
            itemGameAreaController.clearLines();
            Effect = false;
        }
    }

    public boolean getEffect()
    {
        return Effect;
    }

    public boolean getChew()
    {
        return chew;
    }

    public void clearLinesWhenBlockIsBottomButNotEnd()
    {
        itemGameAreaController.moveBlockToBackground();
        itemGameAreaController.spawnBlock2(gen.getArr()[next],1,false);
        int current_line = itemGameAreaController.clearLines2();

        if (current_line > 0) {
            Effect = true;
        }
        lines += current_line;
        score += current_line * current_line*10;
        score += (int) ((1250 - (int) (delay / velocity)) / 100) * current_line + lines * current_line; // delay에 의한 추가 점수
        if (delay >= 100) {
            delay -= (int) current_line * 20 / velocity;

        }
        next++;
    }

    public int[][] getNextBlock() {
        ItemBlockController nextBlock = itemGameAreaController.getNextBlock();
        int[][] now = nextBlock.getShape();
        int color= nextBlock.getColor();

        for (int r = 0; r < now.length; r++) {
            for (int c = 0; c < now[0].length; c++) {
                if (now[r][c] > 0 && now[r][c]<=7) {
                    now[r][c] = color;
                }
            }
        }
        return now;
    }

    public void func1()
    {
        itemGameAreaController.moveBlockToBackground(); //블럭 뒤로 보내고
        int current_lines = itemGameAreaController.clearLines2(); //색을 바꿈
        if (current_lines > 0) {
            Effect = true; //이펙트를 줘야하는것을 알림
        }
        //점수
        lines += current_lines;
        lineIndex += current_lines;
        int current_score = 2 * (current_lines * current_lines + (int) ((1250 - (int) (delay / velocity)) / 100) * current_lines + lines * current_lines);
        //현재 화면 점수 -> 행이 한번에 많이 지워지면 가산점, delay가 적으면 가산점, 지금까지 누적으로 지운 라인이 많으면 가산점
        if (doubleIndex > 0) {
            //점수2배이벤트
            score += 2 * current_score;
            doubleIndex--;
        } else {
            score += current_score;
        }
        //점수

        //블럭 생성.
        if (lineIndex>=10) {
            int c = r2.nextInt(5) + 8; // 0, 1, 2, 3, 4 중에 하나 생성되고, 거기에 8이 더해져서 8, 9, 10, 11, 12가 된다.
            lineIndex -= 10;
            itemGameAreaController.switchBlock(); //원래의 다음블럭을 현재 블럭으로 받아서 생성
            itemGameAreaController.spawnBlock2(gen.getArr()[next], c, true); //새로운 다음 블럭의 정보 생성
        } else {
            itemGameAreaController.switchBlock();
            itemGameAreaController.spawnBlock2(gen.getArr()[next], 1, false);//노말아이템
        }

        if (delay >= 100) {
            delay -= (int) current_lines * 20 / velocity;

        }
        next++;
    }

    public void func2()
    {if (Effect) {
        itemGameAreaController.clearLines();
        Effect = false;
    }
        itemGameAreaController.moveBlockDown2();//아이템 처리는 다 되고 올라갈 일이 없음.





        if (end) {
            itemGameAreaController.moveBlockToBackground();
            itemGameAreaController.spawnBlock2(gen.getArr()[next], 1, false);
            next++;
            chew = false;
            end=false;
        }}
}
