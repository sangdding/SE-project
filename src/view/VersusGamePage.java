package view;

import controller.HashMapParser;
import controller.PageController;
import model.setting.JsonSetting;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class VersusGamePage extends JFrame{
    private JPanel mainPanel;
    private JPanel palyer1Panel;
    private JPanel gameBoardPanel1;
    private JPanel nextBlockPanel1;
    private JPanel scorePanel1;
    private JPanel passedBlockPanel1;
    private JTextPane gameBoardPane1;
    private JTextPane nextBlockPane1;
    private JTextPane passedBlockPane1;
    private JLabel scoreLabel1;
    private JPanel player2Panel;
    private JPanel gameBoardPanel2;
    private JTextPane gameBoardPane2;
    private JPanel nextBlockPanel2;
    private JTextPane nextBlockPane2;
    private JPanel passedBlockPanel2;
    private JTextPane passedBlockPane2;
    private JPanel scorePanel2;
    private JLabel scoreLabel2;
    private JPanel buttonPanel;
    private JButton stopButton;
    private JButton mainButton;
    private JButton exitButton;

    private PageController pageController;

    private final int ItemMode=1;
    private final int BlindMode=1;
    private final int NotBlindMode=0;


    private temp tempClassForPlayer1;
    private temp tempClassForPlayer2;

    public Timer timerForPlyer1;
    public Timer timerForPlyer2;

    private JsonSetting settingForPlayer1 = new JsonSetting("player1");
    private JsonSetting settingForPlayer2 = new JsonSetting("player2");
    private HashMap<String, Integer> keySettingMapForPlayer1;
    private HashMap<String, Integer> keySettingMapForPlayer2;

    private boolean isStop;

    private SimpleAttributeSet styleSet;

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
    public VersusGamePage() {
        //초기화
        initialize();
        //키보드 이벤트 처리 설정
        setKeyEventController();
        //버튼 마우스 입력 처리 설정
        setButtonClickController();
        drawGameBoard(tempClassForPlayer1.getBackground(),1);
        drawGameBoard(tempClassForPlayer2.getBackground(),2);
        if(settingForPlayer1.getGameMode()==ItemMode)
        {
            setTimer2();
        }
        else
        {
            setTimer();

        }
    }
    public void initialize()
    {

        gameMode = settingForPlayer1.getGameMode();
        setStyleDocument();

        tempClassForPlayer1 = new temp();
        tempClassForPlayer2 = new temp();

        //초기 게임 화면 그리기
        gameBoardPane1.setMargin(new Insets(150, 0, 0, 0));
        nextBlockPane1.setMargin(new Insets(300, 0, 0, 0));
        //초기 게임 화면 그리기
        gameBoardPane2.setMargin(new Insets(150, 0, 0, 0));
        nextBlockPane2.setMargin(new Insets(300, 0, 0, 0));

        gameBoardPane1.setEditable(true);
        gameBoardPane2.setEditable(true);

        this.add(mainPanel);


        gameBoardPane1.setEditable(true);
        gameBoardPane2.setEditable(true);

        this.add(mainPanel);
        //설정 읽어오기

        //점수 label 설정
        if (gameMode==0) {//일반전
            scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer1.getDelay()));
            scoreLabel2.setText(Integer.toString(tempClassForPlayer2.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer2.getDelay()));
        }
        else {//아이템전
            scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "   Delay:  " + Integer.toString((int) tempClassForPlayer1.getDelay()) +
                    "   DoblueScore(Left):  " + Integer.toString(tempClassForPlayer1.getDoubleIndex()));
        scoreLabel1.setText(Integer.toString(tempClassForPlayer2.getScore()) + "   Delay:  " + Integer.toString((int) tempClassForPlayer2.getDelay()) +
                    "   DoblueScore(Left):  " + Integer.toString(tempClassForPlayer2.getDoubleIndex()));
        }


        //화면 크기 설정
        this.setSize(1500, 1500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x표 눌럿을 때 프로그램 종료되게 만듦

        //설정한 키 값 불러오기
        HashMapParser hashmapparser = new HashMapParser();
        keySettingMapForPlayer1 = settingForPlayer1.getKeyList();
        keySettingMapForPlayer2 = settingForPlayer2.getKeyList();

        isStop = false;

        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);


        //화면 가운데에 생성
        this.setLocationRelativeTo(null);

    }

    private void setTimer() {
        timerForPlyer1 = new Timer((int) tempClassForPlayer1.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                requestFocus();
                setFocusable(true);



                tempClassForPlayer1.clearLinesInTempClass();
                //여기 바꿈
                tempClassForPlayer1.itemGameAreaController.moveBlockDown();



                drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);



                if (!tempClassForPlayer1.itemGameAreaController.checkBottom()) {
                    if (tempClassForPlayer1.itemGameAreaController.isBlockOuOofBounds()) {
                        //게임 종료시
                        timerForPlyer1.stop();
                        dispose();
                        pageController = new PageController();
                        pageController.setScore(tempClassForPlayer1.getScore());
                        pageController.createGameEndPage();
                    }
                    else {
                        tempClassForPlayer1.clearLinesWhenBlockIsBottomButNotEnd();
                        timerForPlyer1.setDelay((int) tempClassForPlayer1.getDelay());
                        drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                    }

                }

                //화면에 점수 출력
                scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer1.getDelay()));

                //다음 블럭 그리기
                drawNextBlock(tempClassForPlayer1.getNextBlock(),1);

            }

        });

        timerForPlyer2 = new Timer((int) tempClassForPlayer2.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                requestFocus();
                setFocusable(true);



                tempClassForPlayer2.clearLinesInTempClass();
                //여기 바꿈
                tempClassForPlayer2.itemGameAreaController.moveBlockDown();



                drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);



                if (!tempClassForPlayer2.itemGameAreaController.checkBottom()) {
                    if (tempClassForPlayer2.itemGameAreaController.isBlockOuOofBounds()) {
                        //게임 종료시
                        timerForPlyer2.stop();
                        dispose();
                        pageController = new PageController();
                        pageController.setScore(tempClassForPlayer2.getScore());
                        pageController.createGameEndPage();
                    }
                    else {
                        tempClassForPlayer2.clearLinesWhenBlockIsBottomButNotEnd();
                        timerForPlyer2.setDelay((int) tempClassForPlayer2.getDelay());
                        drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                    }

                }

                //화면에 점수 출력
                scoreLabel2.setText(Integer.toString(tempClassForPlayer2.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer2.getDelay()));

                //다음 블럭 그리기
                drawNextBlock(tempClassForPlayer2.getNextBlock(),2);

            }

        });

        timerForPlyer1.start();
        timerForPlyer2.start();
    }


    private void setTimer2() //아이템 모드의 타이머 액션
    {
        timerForPlyer1 = new Timer((int) tempClassForPlayer1.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                requestFocus();
                setFocusable(true);
                if (tempClassForPlayer1.itemGameAreaController.ga.block == null) { //끝난경우
                }
                else if (tempClassForPlayer1.itemGameAreaController.ga.block.shape[0][0] == 9) { //무게추 아이템인 경우
                    tempClassForPlayer1.func2();
                    drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                }

                else {
                    tempClassForPlayer1.clearLinesInTempClass(); //줄을 지우고
                    tempClassForPlayer1.itemGameAreaController.moveBlockDown(); //블럭 1칸 떨어뜨림



                    drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1); //그림



                    if (!tempClassForPlayer1.itemGameAreaController.checkBottom()) { //블럭이 닿았으면
                        if (tempClassForPlayer1.itemGameAreaController.isBlockOuOofBounds()) {
                            //게임 종료
                            // (아이템전)
                            timerForPlyer1.stop();
                            dispose();

                            pageController = new PageController();
                            pageController.setScore(tempClassForPlayer1.getScore());
                            pageController.createGameEndPage();
                        }



                        else {
                            tempClassForPlayer1.func1(); //색을 바꾸고
                            timerForPlyer1.setDelay((int) tempClassForPlayer1.getDelay());
                            drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                        }
                    }

                    //민재 형, 점수 계산 하는 코드 넣어 줘

                    //화면에 점수 출력
                    scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "   Delay:  " + Integer.toString((int) tempClassForPlayer1.getDelay()) + "   DoblueScore(Left):  " + Integer.toString(tempClassForPlayer1.getDoubleIndex()));
                    //다음 블럭 그리기
                    drawNextBlock(tempClassForPlayer1.getNextBlock(),1);
                }
            }

        });
        timerForPlyer2 = new Timer((int) tempClassForPlayer2.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                requestFocus();
                setFocusable(true);
                if (tempClassForPlayer2.itemGameAreaController.ga.block == null) { //끝난경우
                }
                else if (tempClassForPlayer2.itemGameAreaController.ga.block.shape[0][0] == 9) { //무게추 아이템인 경우
                    tempClassForPlayer2.func2();
                    drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                }

                else {
                    tempClassForPlayer2.clearLinesInTempClass(); //줄을 지우고
                    tempClassForPlayer2.itemGameAreaController.moveBlockDown(); //블럭 1칸 떨어뜨림



                    drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2); //그림



                    if (!tempClassForPlayer2.itemGameAreaController.checkBottom()) { //블럭이 닿았으면
                        if (tempClassForPlayer2.itemGameAreaController.isBlockOuOofBounds()) {
                            //게임 종료
                            // (아이템전)
                            timerForPlyer2.stop();
                            dispose();

                            pageController = new PageController();
                            pageController.setScore(tempClassForPlayer2.getScore());
                            pageController.createGameEndPage();
                        }



                        else {
                            tempClassForPlayer2.func1(); //색을 바꾸고
                            timerForPlyer2.setDelay((int) tempClassForPlayer2.getDelay());
                            drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                        }
                    }

                    //민재 형, 점수 계산 하는 코드 넣어 줘

                    //화면에 점수 출력
                    scoreLabel2.setText(Integer.toString(tempClassForPlayer2.getScore()) + "   Delay:  " + Integer.toString((int) tempClassForPlayer2.getDelay()) + "   DoblueScore(Left):  " + Integer.toString(tempClassForPlayer2.getDoubleIndex()));
                    //다음 블럭 그리기
                    drawNextBlock(tempClassForPlayer2.getNextBlock(),2);
                }
            }

        });

        timerForPlyer1.start();
        timerForPlyer2.start();
    }

    private void setStyleDocument()
    {
        styleSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet, 18);
        StyleConstants.setFontFamily(styleSet, "comic sans");
        StyleConstants.setBold(styleSet, false);
        StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);
    }

    private void setButtonClickController() {
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                timerForPlyer1.stop();
                timerForPlyer2.stop();
                dispose();

                pageController = new PageController(e.getActionCommand());
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isStop) {
                    isStop = true;
                    timerForPlyer1.stop();
                    timerForPlyer2.stop();

                    requestFocus();
                    setFocusable(true);

                } else {
                    isStop = false;
                    timerForPlyer1.start();
                    timerForPlyer2.start();
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

    public void drawGameBoard(int[][] background, int index) {

        //이전 화면 지우기
        gameBoardPane.setText("");
        //여기서부턴 화면에 그리기


        drawTextWithColor(gameBoardPane, "XXXXXXXXXXXX\n", Color.BLACK);


        for (int i = 0; i < 20; i++) {
            drawTextWithColor(gameBoardPane, "X", Color.BLACK);

            for (int j = 0; j < 10; j++) {
                //일반 블럭 그리기
                if (background[i][j] < 8) {
                    if (setting.getDisplayMode() ==NotBlindMode ) { //일반모드 그리기
                        drawTextWithColor(gameBoardPane, "X", colorForBlock[background[i][j]]);
                    } else { // 색맹모드 그리기
                        drawTextWithColor(gameBoardPane, "X", colorFOrBlindModeBlock[background[i][j]]);
                    }
                }
                else {
                    if (setting.getDisplayMode() == NotBlindMode) { //일반모드 그리기
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


    private void drawNextBlock(int[][] background, int index) {

        //이전 화면 지우기
        nextBlockPane1.setText("");


        //여기서부턴 화면에 그리기
        //민재 형 이거 만들어 줘
        int nextBlock[][] = background;

        for (int i = 0; i < nextBlock.length; i++) {

            for (int j = 0; j < nextBlock[i].length; j++) {
                if(setting.getDisplayMode()==NotBlindMode) drawTextWithColor(nextBlockPane, "X", colorForBlock[background[i][j]]); //일반 모드
                else drawTextWithColor(nextBlockPane, "XX", colorFOrBlindModeBlock[background[i][j]]); //색맹 모드
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
}
