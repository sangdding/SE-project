package view;

import controller.GameControl.GameAreaController;
import controller.PageController;
import controller.block.ItemBlockController;
import controller.ItemGameControl.ItemGameAreaController;
import model.Generator;
import model.block.ItemBlock;
import model.setting.JsonSetting;
import model.setting.Setting;
import model.block.NormalBlock;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import controller.HashMapParser;
import java.util.Random;
public class GamePage extends JFrame {
    private JPanel mainPanel;
    private javax.swing.JPanel gameBoardPanel;
    private Random r = new Random();
    private Random r2 = new Random();
    private Generator gen;
    private GameAreaController gameAreaController;
    private ItemGameAreaController itemGameAreaController;
    private JButton mainButton;
    private JButton stopButton;
    private JPanel buttonPanel;
    private JPanel nextBlockPanel;
    private JTextPane gameBoardPane;
    private JTextPane nextBlockPane;
    private JPanel scorePanel;
    private JButton exitButton;
    private JLabel scoreLabel;
    private PageController pageController;
    public boolean doubleScore; //아이템 변수
    public int doubleIndex;
    public boolean fifth; //아이템 변수
    public double delay; //아이템 변수
    public double velocity;
    private boolean isStop;
    public Timer timer;
    private int isBlindMode;
    public int score;
    private int lineIndex;
    public boolean end;
    private JsonSetting setting = new JsonSetting();
    private HashMap<String, Integer> keySettingMap;
    private int next;
    public int lines;
    public boolean chew;
    private NormalBlock BlockModel = new NormalBlock();
    private char borderChar = 'X';
    private SimpleAttributeSet styleSet;
    private boolean Effect;
    private Color[] colorForBlock = new Color[]{Color.WHITE,
            Color.CYAN, Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE,
            Color.PINK, new Color(128, 0, 0), new Color(128, 128, 0), new Color(0, 0, 128),
            new Color(128, 0, 128), new Color(0, 139, 139), new Color(255, 105, 180)
            ,new Color(100,100,100)
    }; // white for backgroind + 13 colors + one for flash animation

    private Color[] colorFOrBlindModeBlock = new Color[]{Color.WHITE,
            Color.ORANGE, new Color(135, 206, 235), new Color(60, 179, 113), Color.PINK, Color.BLUE,
            new Color(204, 71, 75), new Color(149, 53, 83), new Color(128, 0, 0), new Color(128, 128, 0), new Color(0, 0, 128),
            new Color(128, 0, 128), new Color(0, 139, 139), new Color(255, 105, 180)
    }; // white for backgroind + 13 colors for blind block

    private char[] blockShape = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'H', 'J', 'L', 'O', 'T', 'S', 'Q', 'O'
    };

    private int gameMode;


    public GamePage() {
        //초기화
        initialize();

        //키보드 이벤트 처리 설정
        setKeyEventController();
        //버튼 마우스 입력 처리 설정
        setButtonClickController();
        if (setting.getGameMode() == 0) {
            drawGameBoard(gameAreaController.getBackground());
            gameAreaController.spawnBlock(gen.getArr()[0]);
            setTimer();
        } else {
            drawGameBoard(itemGameAreaController.getBackground());
            itemGameAreaController.spawnBlock2(gen.getArr()[0], 1, false);
            setTimer2();
        }
    }

    private void initialize() {

        gameMode = setting.getGameMode();

        //스타일
        styleSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet, 18);
        StyleConstants.setFontFamily(styleSet, "comic sans");
        StyleConstants.setBold(styleSet, false);
        StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);

        gameAreaController = new GameAreaController(this);
        itemGameAreaController = new ItemGameAreaController(this);

        //초기 게임 화면 그리기
        gameBoardPane.setMargin(new Insets(150, 0, 0, 0));
        nextBlockPane.setMargin(new Insets(300, 0, 0, 0));

        gameBoardPane.setEditable(true);
        this.add(mainPanel);

        //설정 읽어오기

        //점수 label 설정
        scoreLabel.setText("0");

        //난이도별 생성기 세팅
        this.gen = new Generator(setting.getDifficulty());
        this.next = 1;
        //화면 크기 설정
        HashMap<String, Integer> settingMap = setting.getDisplaySize();
        this.setSize(settingMap.get("width"), settingMap.get("height"));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x표 눌럿을 때 프로그램 종료되게 만듦
        //색맹모드 체크
        isBlindMode = setting.getDisplayMode();

        //설정한 키 값 불러오기
        HashMapParser hashmapparser = new HashMapParser();
        keySettingMap = setting.getKeyList();
        System.out.println(keySettingMap);


        isStop = false;

        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);


        //화면 가운데에 생성
        this.setLocationRelativeTo(null);
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
    }


    private void setTimer() {
        timer = new Timer((int) delay, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                requestFocus();
                setFocusable(true);
                if (Effect) {
                    gameAreaController.clearLines();
                    Effect = false;
                } //여기 바꿈
                gameAreaController.moveBlockDown();
                drawGameBoard(gameAreaController.newBackground());
                if (!gameAreaController.checkBottom()) {
                    if (gameAreaController.isBlockOuOofBounds()) {
                        //게임 종료시
                        timer.stop();
                        dispose();
                        pageController = new PageController();
                        pageController.setScore(score);
                        pageController.createGameEndPage();
                    }
                    else {
                        gameAreaController.moveBlockToBackground();
                        gameAreaController.spawnBlock(gen.getArr()[next]);
                        int current_line = gameAreaController.clearLines2();
                        drawGameBoard(gameAreaController.newBackground());
                        if (current_line > 0) {
                            Effect = true;
                        }
                        lines += current_line;
                        score += current_line * current_line*10;
                        score += (int) ((1250 - (int) (delay / velocity)) / 100) * current_line + lines * current_line; // delay에 의한 추가 점수
                        if (delay >= 100) {
                            delay -= (int) current_line * 20 / velocity;
                            timer.setDelay((int) delay);
                        }
                        next++;
                    }
                }

                //화면에 점수 출력
                scoreLabel.setText(Integer.toString(score) + "delay:" + Integer.toString((int) delay));

                //다음 블럭 그리기
                drawNextBlock(getNextBlock());

            }

        });
        timer.start();
    }

    private void setTimer2() //아이템 모드의 타이머 액션
    {
        timer = new Timer((int) delay, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                requestFocus();
                setFocusable(true);
                if (itemGameAreaController.ga.block == null) {
                } else if (itemGameAreaController.ga.block.shape[0][0] == 9) {
                    if (Effect) {
                        itemGameAreaController.clearLines();
                        Effect = false;
                    }
                    itemGameAreaController.moveBlockDown2();//아이템 처리는 다 되고 올라갈 일이 없음.
                    drawGameBoard(itemGameAreaController.newBackground());
                    if (end) {
                        itemGameAreaController.moveBlockToBackground();
                        itemGameAreaController.spawnBlock2(gen.getArr()[next], 1, false);
                        next++;
                        chew = false;
                        end=false;
                    }
                } else {
                    if (Effect) {
                        itemGameAreaController.clearLines();
                        Effect = false;
                    }
                    itemGameAreaController.moveBlockDown();
                    drawGameBoard(itemGameAreaController.newBackground());
                    if (!itemGameAreaController.checkBottom()) {

                        if (itemGameAreaController.isBlockOuOofBounds()) {
                            //게임 종료
                            // (아이템전)
                            System.out.print("END");
                            timer.stop();
                            dispose();

                            pageController = new PageController();
                            pageController.setScore(score);
                            pageController.createGameEndPage();
                        } else {
                            itemGameAreaController.moveBlockToBackground();
                            int current_lines = itemGameAreaController.clearLines2();
                            if (current_lines > 0) {
                                Effect = true;
                            }
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
                            if (lineIndex>=10) {
                                int c = r2.nextInt(5) + 8; // 0, 1, 2, 3, 4 중에 하나 생성되고, 거기에 8이 더해져서 8, 9, 10, 11, 12가 된다.
                                itemGameAreaController.spawnBlock2(gen.getArr()[next], c, true);
                                lineIndex -= 10;
                            } else {
                                itemGameAreaController.spawnBlock2(gen.getArr()[next], 1, false);//노말아이템
                            }
                            System.out.print("COLOR+" + itemGameAreaController.ga.block.getColor());
                            System.out.print(BlockModel.getColor(gen.getArr()[next]));
                            drawGameBoard(itemGameAreaController.newBackground());
                            if (delay >= 100) {
                                delay -= (int) current_lines * 20 / velocity;
                                timer.setDelay((int) delay);
                            }
                            next++;
                        }
                    }

                    //민재 형, 점수 계산 하는 코드 넣어 줘

                    //화면에 점수 출력
                    scoreLabel.setText(Integer.toString(score) + "Delay:  " + Integer.toString((int) delay) + "DoblueScore(Left):  " + Integer.toString(doubleIndex));
                    //다음 블럭 그리기
                    drawNextBlock(getNextBlock());
                }
            }

        });

        timer.start();
    }

    private void setKeyEventController() {
        addKeyListener(new KeyAdapter() { //키 이벤트
            @Override
            public void keyPressed(KeyEvent e) { //키 눌렀을때
                System.out.println(e.getKeyCode());
                // TODO Auto-generated method stub
                //원래 switch case문인데, case에 constant 값만 들어갈 수 있어서 if로 교체
                int pressedKey = e.getKeyCode();

                if (pressedKey == keySettingMap.get("resume")) {
                    if (isStop) {
                        System.out.println("Game Restarted");
                        isStop = false;
                        timer.start();
                    }
                }
                else if (pressedKey == keySettingMap.get("drop")) {
                    if (setting.getGameMode() == 0) {
                        if (gameAreaController.ga.block == null || Effect) {
                        } else {

                            gameAreaController.dropBlock();
                            drawGameBoard(gameAreaController.newBackground());
                        }
                    } else {
                        if (itemGameAreaController.ga.block.shape[0][0] == 9) {
                            if (chew) {
                            } else {
                                itemGameAreaController.dropBlock();
                                drawGameBoard(itemGameAreaController.newBackground());
                            }
                        } else {
                            if (itemGameAreaController.ga.block == null) {
                            } else {
                                itemGameAreaController.dropBlock();
                                drawGameBoard(itemGameAreaController.newBackground());
                            }
                        }
                    }
                }
                else if (pressedKey == keySettingMap.get("exit")) {
                    timer.stop();
                    dispose();

                    pageController = new PageController("Main");

                }
                else if ((setting.getGameMode() == 0 && gameAreaController.ga.block == null) ||
                        (setting.getGameMode() == 1 && itemGameAreaController.ga.block == null)) {
                }
                else if (pressedKey == keySettingMap.get("left")) {
                    if (setting.getGameMode() == 0) {
                        if (gameAreaController.ga.block == null) {
                        } else {
                            gameAreaController.moveBlockLeft();
                            drawGameBoard(gameAreaController.newBackground());
                        }
                    }
                    else {
                        if (itemGameAreaController.ga.block.shape[0][0] == 9) {
                            if (chew) {
                            } else {
                                itemGameAreaController.moveBlockLeft();
                                drawGameBoard(itemGameAreaController.newBackground());
                            }
                        } else {
                            if (itemGameAreaController.ga.block == null) {
                            } else {
                                itemGameAreaController.moveBlockLeft();
                                drawGameBoard(itemGameAreaController.newBackground());
                            }
                        }
                    }

                }
                else if (pressedKey == keySettingMap.get("rotate")) {
                    if (setting.getGameMode() == 0) {
                        if (gameAreaController.ga.block == null) {
                        } else {
                            gameAreaController.rotateBlock();
                            drawGameBoard(gameAreaController.newBackground());
                        }
                    } else {
                        if (itemGameAreaController.ga.block.shape[0][0] == 9) {
                        } else {
                            if (itemGameAreaController.ga.block == null) {
                            } else {
                                itemGameAreaController.rotateBlock();
                                drawGameBoard(itemGameAreaController.newBackground());
                            }

                        }
                    }
                }
                else if (pressedKey == keySettingMap.get("right")) {
                    if (setting.getGameMode() == 0) {
                        if (gameAreaController.ga.block == null) {
                        } else {
                            gameAreaController.moveBlockRight();
                            drawGameBoard(gameAreaController.newBackground());
                        }
                    } else {
                        if (itemGameAreaController.ga.block.shape[0][0] == 9) {
                            if (chew) {
                            } else {
                                itemGameAreaController.moveBlockRight();
                                drawGameBoard(itemGameAreaController.newBackground());
                            }
                        } else {
                            if (itemGameAreaController.ga.block == null) {
                            } else {
                                itemGameAreaController.moveBlockRight();
                                drawGameBoard(itemGameAreaController.newBackground());
                            }
                        }
                    }
                }
                else if (pressedKey == keySettingMap.get("down")) {
                    if (setting.getGameMode() == 0) {
                        if (gameAreaController.ga.block == null) {
                        } else {
                            gameAreaController.moveBlockDown();
                            drawGameBoard(gameAreaController.newBackground());
                        }
                    }
                    else {
                        if (itemGameAreaController.ga.block == null || chew) {
                        } else if (itemGameAreaController.ga.block.shape[0][0] == 9){
                            itemGameAreaController.moveBlockDown2();
                            drawGameBoard(itemGameAreaController.newBackground());
                        }
                        else{
                            itemGameAreaController.moveBlockDown();
                            drawGameBoard(itemGameAreaController.newBackground());
                        }
                    }
                }
                else if (pressedKey == keySettingMap.get("pause")) {
                    if (!isStop) {
                        System.out.println("Game Stoopped");
                        isStop = true;
                        timer.stop();
                    }
                }
            }
        });
    }

    private void setButtonClickController() {
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                timer.stop();
                dispose();

                pageController = new PageController(e.getActionCommand());
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isStop) {
                    isStop = true;
                    timer.stop();

                    requestFocus();
                    setFocusable(true);

                } else {
                    isStop = false;
                    timer.start();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    public void drawGameBoard(int[][] background) {

        //이전 화면 지우기
        gameBoardPane.setText("");
        //여기서부턴 화면에 그리기


        drawTextWithColor(gameBoardPane, "XXXXXXXXXXXX\n", Color.BLACK);


        for (int i = 0; i < 20; i++) {
            drawTextWithColor(gameBoardPane, "X", Color.BLACK);

            for (int j = 0; j < 10; j++) {
                //일반 블럭 그리기
                if (background[i][j] < 8) {
                    if (gameMode == 0) { //일반모드 그리기
                        drawTextWithColor(gameBoardPane, "X", colorForBlock[background[i][j]]);
                    } else { // 색맹모드 그리기
                        drawTextWithColor(gameBoardPane, "X", colorFOrBlindModeBlock[background[i][j]]);
                    }
                }
                else {
                    if (gameMode == 0) { //일반모드 그리기
                        drawTextWithColor(gameBoardPane, String.valueOf(blockShape[background[i][j]]), colorForBlock[background[i][j]]);
                    } else { // 색맹모드 그리기
                        drawTextWithColor(gameBoardPane, String.valueOf(blockShape[background[i][j]]), colorFOrBlindModeBlock[background[i][j]]);
                    }
                }

            }
            drawTextWithColor(gameBoardPane, "X\n", Color.BLACK);
        }


        drawTextWithColor(gameBoardPane, "XXXXXXXXXXXX", Color.BLACK);

        //이거 없어도 보드는 그려진다. 뭔가 스타일 관련 코드인 듯
        StyledDocument doc = gameBoardPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        gameBoardPane.setStyledDocument(doc);

    }


    private void drawNextBlock(int[][] background) {

        //이전 화면 지우기
        nextBlockPane.setText("");


        //여기서부턴 화면에 그리기
        //민재 형 이거 만들어 줘
        int nextBlock[][] = background;

        for (int i = 0; i < nextBlock.length; i++) {

            for (int j = 0; j < nextBlock[i].length; j++) {
                if(gameMode==0) drawTextWithColor(nextBlockPane, "X", colorForBlock[background[i][j]]); //일반 모드
                else drawTextWithColor(nextBlockPane, "X", colorFOrBlindModeBlock[background[i][j]]); //색맹 모드
            }

            drawTextWithColor(nextBlockPane, "\n", Color.BLACK);
        }
        //이거 없어도 보드는 그려진다. 뭔가 스타일 관련 코드인 듯
        StyledDocument doc = nextBlockPane.getStyledDocument();


        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        nextBlockPane.setStyledDocument(doc);

    }

    private void drawTextWithColor(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();

        tp.setCaretPosition(len);

        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);

    }

    int[][] getNextBlock() {
        int[][] now = BlockModel.normalBlock[gen.getArr()[next]];
        int color;
        if (setting.getGameMode() == 0) {
            color = BlockModel.getColor(gen.getArr()[next]);
        } else {
            color = BlockModel.getColor(gen.getArr()[next]);
        }
        for (int r = 0; r < now.length; r++) {
            for (int c = 0; c < now[0].length; c++) {
                if (now[r][c] != 0) {
                    now[r][c] = color;
                }
            }
        }
        return now;
    }

}