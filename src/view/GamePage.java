package view;

import controller.GameControl.GameAreaController;
import controller.PageController;
import model.Generator;
import model.setting.JsonSetting;
import model.setting.Setting;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import controller.HashMapParser;
import java.util.Random;
public class GamePage extends JFrame{
    private JPanel mainPanel;
    private javax.swing.JPanel gameBoardPanel;
    private Random r = new Random();
    private Generator gen;
    private GameAreaController GameAreaController;
    private JButton mainButton;
    private JButton stopButton;
    private JPanel buttonPanel;
    private JPanel nextBlockPanel;
    private JTextPane gameBoardPane;
    private JTextPane nextBlockPane;
    private JPanel scorePanel;
    private JButton exitButton;
    private PageController pageController;
    private int score;
    private boolean isStop;
    private Timer timer;
    private int isBlindMode;
    private JsonSetting setting;
    private HashMap<String,Integer> keySettingMap;
    private int next;


    private GameAreaController gameAreaController = new GameAreaController();
    private char borderChar='X';
    private SimpleAttributeSet styleSet;

    private Color []  colorForBlock = new Color[] {
            Color.CYAN,Color.RED,Color.BLUE,Color.GREEN,Color.MAGENTA,Color.ORANGE,
            Color.YELLOW, new Color(128,0,0), new Color(128,128,0), new Color(0,0,128),
            new Color(128,0,128), new Color(0,139,139), new Color(255,105,180)
    }; // 13 colors;

    private Color [] colorFOrBlindModeBlock = new  Color[]{
            Color.ORANGE, new Color(135,206,235), new Color(60,179,113), Color.YELLOW, Color.BLUE,
            new Color(204,71,75), new Color(149, 53, 83), new Color(128,0,0), new Color(128,128,0), new Color(0,0,128),
            new Color(128,0,128), new Color(0,139,139), new Color(255,105,180)
    }; // 13 colors for blind

    private char [] blockShape = new char [] {
            'A', 'B', 'C', 'D','E','F','H','J','K','M','N','P','S'
    };



    public GamePage() {
        //초기화
        initialize();

        //키보드 이벤트 처리 설정
        setKeyEventController();
        //버튼 마우스 입력 처리 설정
        setButtonClickController();

        //timer 설정
        setTimer();
    }

    private void initialize(){

        //스타일
        styleSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet, 18);
        StyleConstants.setFontFamily(styleSet, "comic sans");
        StyleConstants.setBold(styleSet, false);
        StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);


        //초기 게임 화면 그리기
        gameBoardPane.setMargin(new Insets(130,0,0,0));
        drawGameBoard(gameAreaController.getBackground());

        gameBoardPane.setEditable(true);
        this.add(mainPanel);

        //설정 읽어오기
        Setting setting = new JsonSetting();
        //난이도별 생성기 세팅
        int a=0;int b=0;
        switch(setting.getDifficulty()) {
            case 0: //normal
                a = 142;
                b = 142;
                break;
            case 1: //easy
                a = 170;
                b = 114;
                break;
            case 2: //hard
                a = 114;
                b = 170;
                break;
        }
        Generator gen = new Generator(a,b);
        this.next = r.nextInt(1000);
        this.gen = gen;
        gen.count();
        gameAreaController.spawnBlock(gen.getArr()[r.nextInt(1000)]);
        //화면 크기 설정
        HashMap<String,Integer> settingMap = setting.getDisplaySize();
        this.setSize(settingMap.get("width"), settingMap.get("height"));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x표 눌럿을 때 프로그램 종료되게 만듦
        //색맹모드 체크
        isBlindMode=setting.getDisplayMode();

        //설정한 키 값 불러오기
        HashMapParser hashmapparser=new HashMapParser();
        keySettingMap=setting.getKeyList();
        System.out.println(keySettingMap);


        if(isBlindMode==1) {
            System.out.println("blind mode printed in gamePage");
        }else if(isBlindMode==0){
            System.out.println("normal Display mode printed in gamePage");
        }


        isStop=false;

        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);



        //화면 가운데에 생성
        this.setLocationRelativeTo(null);
        //블럭 생성


    }

    public void setScore(int score){
        this.score=score;
    }

    private void setTimer()
    {
        timer = new Timer(1000, new ActionListener()

        {

            public void actionPerformed (ActionEvent e)

            {
                requestFocus();
                setFocusable(true);

                System.out.println("timer activated in Game page");
                gameAreaController.moveBlockDown();

                drawGameBoard(gameAreaController.newBackground());
                if(!gameAreaController.checkBottom())
                {
                    gameAreaController.moveBlockToBackground();
                    gameAreaController.spawnBlock(gen.getArr()[next]);
                    int current_score=gameAreaController.clearLines();
                    drawGameBoard(gameAreaController.newBackground());
                    score+=current_score*current_score;
                    next=r.nextInt(1000);
                }
            }

        });
        timer.start();
    }


    private void setKeyEventController()
    {
        addKeyListener(new KeyAdapter() { //키 이벤트
            @Override
            public void keyPressed(KeyEvent e) { //키 눌렀을때
                System.out.println("game page key event enter in GamePage");
                System.out.println(e.getKeyCode());
                // TODO Auto-generated method stub
                //원래 switch case문인데, case에 constant 값만 들어갈 수 있어서 if로 교체
                int pressedKey=e.getKeyCode();

                if(pressedKey==keySettingMap.get("resume")){

                    if(isStop)
                    {
                        System.out.println("Game Restarted");
                        isStop=false;
                        timer.start();
                    }
                }
                else if(pressedKey==keySettingMap.get("drop")){
                    System.out.println("d");
                    gameAreaController.dropBlock();
                    drawGameBoard(gameAreaController.newBackground());
                }
                else if(pressedKey==keySettingMap.get("exit")){
                    System.out.println("esc");
                    timer.stop();
                    dispose();
                    pageController = new PageController("Main");

                }
                else if(pressedKey==keySettingMap.get("left")){
                    System.out.println("l");
                    gameAreaController.moveBlockLeft();
                    drawGameBoard(gameAreaController.newBackground());
                }
                else if(pressedKey==keySettingMap.get("rotate")){
                    System.out.println("u");
                    gameAreaController.rotateBlock();
                    drawGameBoard(gameAreaController.newBackground());
                }
                else if(pressedKey==keySettingMap.get("right")){
                    System.out.println("right");
                    gameAreaController.moveBlockRight();
                    drawGameBoard(gameAreaController.newBackground());
                }
                else if(pressedKey==keySettingMap.get("down")){
                    System.out.println("down");
                    gameAreaController.moveBlockDown();
                    drawGameBoard(gameAreaController.newBackground());
                }
                else if(pressedKey==keySettingMap.get("pause")){
                    if(!isStop) {
                        System.out.println("Game Stoopped");
                        isStop=true;
                        timer.stop();
                    }

                }


            }
        });

    }

    private void setButtonClickController()
    {
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
                if(!isStop) {
                    System.out.println("Game Stoopped");
                    isStop=true;
                    timer.stop();

                    requestFocus();
                    setFocusable(true);

                }
                else
                {
                    System.out.println("Game Restarted");
                    isStop=false;
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

    private void drawGameBoard(int[][] background)
    {

        //이전 화면 지우기
        gameBoardPane.setText("");
        //여기서부턴 화면에 그리기



        drawTextWithColor(gameBoardPane,"XXXXXXXXXXXX\n",Color.BLACK);

       /* for(int i=0; i<20; i++){
            for(int j=0; j<10; j++){
                System.out.print(background[i][j]);
            }
            System.out.println();
        }*/
        for(int i=0;i<20;i++)
        {
            drawTextWithColor(gameBoardPane,"X",Color.BLACK);

            for(int j=0;j<10;j++)
            {
                if(background[i][j]==0) drawTextWithColor(gameBoardPane,"A",Color.WHITE);
                else if (background[i][j] != 0){
                    System.out.println("call the Blue Coloring");
                    drawTextWithColor(gameBoardPane,"B",Color.BLUE);
                }


            }
            drawTextWithColor(gameBoardPane,"X\n",Color.BLACK);

        }


        drawTextWithColor(gameBoardPane,"XXXXXXXXXXXX",Color.BLACK);

        System.out.println("draw end");
        //이거 없어도 보드는 그려진다. 뭔가 스타일 관련 코드인 듯
        StyledDocument doc = gameBoardPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        gameBoardPane.setStyledDocument(doc);


        /* 이건 블럭 별로 색깔 넣기
        StyledDocument doc = pane.getStyledDocument();
		SimpleAttributeSet styles = new SimpleAttributeSet();
		StyleConstants.setForeground(styles, curr.getColor());
        * */
    }

    private void drawTextWithColor(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();

        tp.setCaretPosition(len);

        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);

    }


}


