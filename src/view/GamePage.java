package view;

import controller.GameControl.GameAreaController;
import controller.PageController;
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

public class GamePage extends JFrame{
    private JPanel mainPanel;
    private javax.swing.JPanel gameBoardPanel;

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
    private HashMap<String,Integer> keySettingMap;


    private GameAreaController gameAreaController = new GameAreaController();
    private char borderChar='X';
    private SimpleAttributeSet styleSet;


    public GamePage() {
        //초기화
        initialize();

        //키보드 이벤트 처리 설정
        setKeyEventController();
        //버튼 마우스 입력 처리 설정
        setButtonClickController();
        gameAreaController.spawnBlock(1);
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

        //블록 생성, 초기 게임 화면 그리기
        //gameAreaController.spawnBlock(1);

        gameBoardPane.setEditable(false);
        this.add(mainPanel);

        //설정 읽어오기
        Setting setting = new JsonSetting();
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
                System.out.println("timer activated in Game page");
                gameAreaController.moveBlockDown();
                System.out.println(gameAreaController.getY());
                for(int i=0; i<20; i++){
                    for(int j=0; j<10; j++){
                        System.out.print(gameAreaController.getBackground()[i][j]);
                    }
                    System.out.println();
                }
                drawGameBoard(gameAreaController.getBackground());
                if(!gameAreaController.checkBottom())
                {
                    gameAreaController.moveBlockToBackground();
                    gameAreaController.spawnBlock(1);
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
                }
                else if(pressedKey==keySettingMap.get("exit")){
                    System.out.println("esc");
                }
                else if(pressedKey==keySettingMap.get("left")){
                    System.out.println("l");
                }
                else if(pressedKey==keySettingMap.get("up")){
                    System.out.println("u");
                }
                else if(pressedKey==keySettingMap.get("right")){
                    System.out.println("right");
                }
                else if(pressedKey==keySettingMap.get("down")){
                    System.out.println("down");
                }
                else if(pressedKey==keySettingMap.get("pause")){
                    if(!isStop) {
                        System.out.println("Game Stoopped");
                        isStop=true;
                        timer.stop();
                    }

                }
                else if(pressedKey==keySettingMap.get("quickDown")){
                    System.out.println("q");
                }

            }
        });

    }

    private void setButtonClickController()
    {
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
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
        gameBoardPane.setMargin(new Insets(130,0,0,0));
        //여기서부턴 화면에 그리기
        drawTextWithColor(gameBoardPane,"XXXXXXXXXXXX\n",Color.BLACK);
        for(int i=0;i<20;i++)
        {
            drawTextWithColor(gameBoardPane,"X",Color.BLACK);
            for(int j=0;j<10;j++)
            {
                if(background[i][j]==0) drawTextWithColor(gameBoardPane,"A",Color.WHITE);
                else drawTextWithColor(gameBoardPane,"A",Color.BLUE);
            }
            drawTextWithColor(gameBoardPane,"X\n",Color.BLACK);
        }


        drawTextWithColor(gameBoardPane,"XXXXXXXXXXXX",Color.BLACK);


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
