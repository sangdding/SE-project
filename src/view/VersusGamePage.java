package view;

import controller.HashMapParser;
import controller.PageController;
import model.setting.JsonSetting;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private JLabel timeLabel;
    private JLabel timeNameLabel;

    private JTextPane gameBoardPaneArray[] = {gameBoardPane1,gameBoardPane2};
    private JTextPane nextBlockPaneArray[] = {nextBlockPane1,nextBlockPane2};

    private PageController pageController;

    private final int ItemMode=1;
    private final int NormalMode=0;
    private final int TimerMode=2;
    private final int BlindMode=1;
    private final int NotBlindMode=0;
    public int receive;
    public int[][] toSend;

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
            'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'L', 'O', 'T', 'S', 'Q', 'O'
    };


    private double limitTime=300;

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
        else if(settingForPlayer1.getGameMode()==NormalMode)
        {
            setTimer();

        }
        else if(settingForPlayer1.getGameMode()==TimerMode)
        {
            setTimer3();
        }

    }
    public void initialize()
    {


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

        //점수 label 초기화
        if (settingForPlayer1.getGameMode()==NormalMode || settingForPlayer1.getGameMode()==TimerMode) {//일반전 또는 타이머 모드
            scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer1.getDelay()));
            scoreLabel2.setText(Integer.toString(tempClassForPlayer2.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer2.getDelay()));
        }
        else {//아이템전
            scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "   Delay:  " + Integer.toString((int) tempClassForPlayer1.getDelay()) +
                    "   DoblueScore(Left):  " + Integer.toString(tempClassForPlayer1.getDoubleIndex()));
        scoreLabel1.setText(Integer.toString(tempClassForPlayer2.getScore()) + "   Delay:  " + Integer.toString((int) tempClassForPlayer2.getDelay()) +
                    "   DoblueScore(Left):  " + Integer.toString(tempClassForPlayer2.getDoubleIndex()));
        }

        //타이머 모드 타이머 초기화
        if(settingForPlayer1.getGameMode()==TimerMode)
        {
            timeLabel.setText(Double.toString(limitTime));
        }
        //일반모드나 아이템모드면 타이머 라벨 공란으로
        else{
            timeNameLabel.setText("");
            timeLabel.setText("");
        }


        //화면 크기 설정
        this.setSize(1000, 1000);
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
        this.receive=0;

    }

    private void setTimer()//일반모드 타이머
    {
        timerForPlyer1 = new Timer((int) tempClassForPlayer1.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                requestFocus();
                setFocusable(true);



                tempClassForPlayer1.clearLinesInTempClass();
                //블럭 다운 하기 전에 받을 게 있는 지 확인.
                if(receive==1){
                    tempClassForPlayer1.itemGameAreaController.receive(toSend);
                    receive=0;
                    toSend=null;
                }
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
                        int[][] rowsToSend = tempClassForPlayer1.itemGameAreaController.rowsToSend();
                        if(rowsToSend!=null){

                            toSend=rowsToSend;
                            receive=2;
                            rowsToSend=null;
                        }

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
                if(receive==2){
                    Debug(toSend);
                    tempClassForPlayer2.itemGameAreaController.receive(toSend);
                    receive=0;
                    toSend=null;
                }
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
                        int[][] rowsToSend = tempClassForPlayer2.itemGameAreaController.rowsToSend();
                        if(rowsToSend!=null){
                            toSend=rowsToSend;
                            receive=1;
                            rowsToSend=null;
                        }
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
                    if(receive==1){
                        tempClassForPlayer1.itemGameAreaController.receive(toSend);
                        receive=0;
                        toSend=null;
                    }
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
                            int[][] rowsToSend = tempClassForPlayer1.itemGameAreaController.rowsToSend();
                            if(rowsToSend!=null){

                                toSend=rowsToSend;
                                receive=2;
                                rowsToSend=null;
                            }
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
                    if(receive==2){
                        Debug(toSend);
                        tempClassForPlayer2.itemGameAreaController.receive(toSend);
                        receive=0;
                        toSend=null;
                    }
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
                            int[][] rowsToSend = tempClassForPlayer2.itemGameAreaController.rowsToSend();
                            if(rowsToSend!=null){
                                toSend=rowsToSend;
                                receive=1;
                                rowsToSend=null;
                            }
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
    private void setTimer3() //타이머 모드의 타이머
    {
        timerForPlyer1 = new Timer((int) tempClassForPlayer1.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                requestFocus();
                setFocusable(true);

                //제한시간 줄어듦
                limitTime-=(tempClassForPlayer1.getDelay()/1000);
                timeLabel.setText(Double.toString(limitTime));

                //제한시간이 0이하면 게임 종료
                if(limitTime<0)
                {
                    
                }


                tempClassForPlayer1.clearLinesInTempClass();
                //여기 바꿈
                if(receive==1){
                    tempClassForPlayer1.itemGameAreaController.receive(toSend);
                    receive=0;
                    toSend=null;
                }
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
                        int[][] rowsToSend = tempClassForPlayer1.itemGameAreaController.rowsToSend();
                        if(rowsToSend!=null){

                            toSend=rowsToSend;
                            receive=2;
                            rowsToSend=null;
                        }
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
                if(receive==2){
                    Debug(toSend);
                    tempClassForPlayer2.itemGameAreaController.receive(toSend);
                    receive=0;
                    toSend=null;
                }
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
                        int[][] rowsToSend = tempClassForPlayer2.itemGameAreaController.rowsToSend();
                        if(rowsToSend!=null){
                            toSend=rowsToSend;
                            receive=1;
                            rowsToSend=null;
                        }
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
    private void setStyleDocument()
    {
        styleSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet, 18);
        StyleConstants.setFontFamily(styleSet, "comic sans");
        StyleConstants.setBold(styleSet, false);
        StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);
    }


    private void setKeyEventController() {
        addKeyListener(new KeyAdapter() { //키 이벤트
            @Override
            public void keyPressed(KeyEvent e) { //키 눌렀을때
                // TODO Auto-generated method stub
                //원래 switch case문인데, case에 constant 값만 들어갈 수 있어서 if로 교체
                int pressedKey = e.getKeyCode();

                if (pressedKey == keySettingMapForPlayer1.get("resume")) {
                    if (isStop) {
                        isStop = false;
                        timerForPlyer1.start();
                        timerForPlyer2.start();
                    }
                }
                else if (pressedKey == keySettingMapForPlayer1.get("exit")) {
                    timerForPlyer1.stop();
                    timerForPlyer2.stop();
                    dispose();

                    pageController = new PageController("Main");

                }
                else if (pressedKey == keySettingMapForPlayer1.get("pause")) {
                    if (!isStop) {
                        isStop = true;
                        timerForPlyer1.stop();
                        timerForPlyer2.stop();
                    }
                }


                //player1 key setting
                else if (pressedKey == keySettingMapForPlayer1.get("drop")) {
                    if (settingForPlayer1.getGameMode() == NormalMode || settingForPlayer1.getGameMode()==TimerMode) {
                        // 일반모드, 타이머 모드
                        if (tempClassForPlayer1.itemGameAreaController.ga.block == null || tempClassForPlayer1.getEffect()) {
                        } else {

                            tempClassForPlayer1.itemGameAreaController.dropBlock();
                            drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                        }
                    }
                    else {
                        //아이템전
                        if (tempClassForPlayer1.itemGameAreaController.ga.block.shape[0][0] == 9) {
                            if (tempClassForPlayer1.getChew()) {
                            } else {
                                tempClassForPlayer1.itemGameAreaController.dropBlock();
                                drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                            }
                        }
                        else {
                            if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                            } else {
                                tempClassForPlayer1.itemGameAreaController.dropBlock();
                                drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                            }
                        }
                    }
                }

                else if ((settingForPlayer1.getGameMode() == 0 && tempClassForPlayer1.itemGameAreaController.ga.block == null) ||
                        (settingForPlayer1.getGameMode() == 1 && tempClassForPlayer1.itemGameAreaController.ga.block == null))
                {
                }
                else if (pressedKey == keySettingMapForPlayer1.get("left")) {
                    if (settingForPlayer1.getGameMode() == NormalMode || settingForPlayer1.getGameMode()==TimerMode) {
                        //일반전 혹은 타이머
                        if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClassForPlayer1.itemGameAreaController.moveBlockLeft();
                            drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                        }
                    }
                    else {
                        //아이템전
                        if (tempClassForPlayer1.itemGameAreaController.ga.block.shape[0][0] == 9) {
                            if (tempClassForPlayer1.getChew()) {
                            } else {
                                tempClassForPlayer1.itemGameAreaController.moveBlockLeft();
                                drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                            }
                        } else {
                            if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                            } else {
                                tempClassForPlayer1.itemGameAreaController.moveBlockLeft();
                                drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                            }
                        }
                    }

                }
                else if (pressedKey == keySettingMapForPlayer1.get("rotate")) {
                    if (settingForPlayer1.getGameMode() == NormalMode || settingForPlayer1.getGameMode()==TimerMode) {
                        //일반모드 혹은 타이머모드
                        if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClassForPlayer1.itemGameAreaController.rotateBlock();
                            drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                        }
                    } 
                    else {
                        //아이템전
                        if (tempClassForPlayer1.itemGameAreaController.ga.block.shape[0][0] == 9) {
                        } else {
                            if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                            } else {
                                tempClassForPlayer1.itemGameAreaController.rotateBlock();
                                drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                            }

                        }
                    }
                }
                else if (pressedKey == keySettingMapForPlayer1.get("right")) {
                    if (settingForPlayer1.getGameMode() == NormalMode || settingForPlayer1.getGameMode()==TimerMode) {
                        //일반모드 혹은 타이머모드
                        if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClassForPlayer1.itemGameAreaController.moveBlockRight();
                            drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                        }
                    }
                    else {
                        //아이템전
                        if (tempClassForPlayer1.itemGameAreaController.ga.block.shape[0][0] == 9) {
                            if (tempClassForPlayer1.getChew()) {
                            } else {
                                tempClassForPlayer1.itemGameAreaController.moveBlockRight();
                                drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                            }
                        } else {
                            if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                            } else {
                                tempClassForPlayer1.itemGameAreaController.moveBlockRight();
                                drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                            }
                        }
                    }
                }
                else if (pressedKey == keySettingMapForPlayer1.get("down")) {
                    if (settingForPlayer1.getGameMode() == NormalMode || settingForPlayer1.getGameMode()==TimerMode) {
                        //일반모드 혹은 타이머모드
                        if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClassForPlayer1.itemGameAreaController.moveBlockDown();
                            drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                        }
                    }
                    else {
                        //아이템모드
                        if (tempClassForPlayer1.itemGameAreaController.ga.block == null || tempClassForPlayer1.getChew()) {
                        } else if (tempClassForPlayer1.itemGameAreaController.ga.block.shape[0][0] == 9){
                            tempClassForPlayer1.itemGameAreaController.moveBlockDown2();
                            drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                        }
                        else{
                            tempClassForPlayer1.itemGameAreaController.moveBlockDown();
                            drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                        }
                    }
                }


                //player2 key setting
                else if (pressedKey == keySettingMapForPlayer2.get("drop")) {
                    if (settingForPlayer1.getGameMode() == NormalMode || settingForPlayer1.getGameMode()==TimerMode) {
                        //일반전 혹은 타이머 모드
                        if (tempClassForPlayer2.itemGameAreaController.ga.block == null || tempClassForPlayer2.getEffect()) {
                        } else {

                            tempClassForPlayer2.itemGameAreaController.dropBlock();
                            drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                        }
                    }
                    else {
                        //아이템전
                        if (tempClassForPlayer2.itemGameAreaController.ga.block.shape[0][0] == 9) {
                            if (tempClassForPlayer2.getChew()) {
                            } else {
                                tempClassForPlayer2.itemGameAreaController.dropBlock();
                                drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                            }
                        } else {
                            if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                            } else {
                                tempClassForPlayer2.itemGameAreaController.dropBlock();
                                drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                            }
                        }
                    }
                }

                else if ((settingForPlayer2.getGameMode() == 0 && tempClassForPlayer2.itemGameAreaController.ga.block == null) ||
                        (settingForPlayer2.getGameMode() == 1 && tempClassForPlayer2.itemGameAreaController.ga.block == null)) {
                }
                else if (pressedKey == keySettingMapForPlayer2.get("left")) {
                    if (settingForPlayer1.getGameMode() == NormalMode || settingForPlayer1.getGameMode()==TimerMode) {
                        //일반모드 혹은 타이머모드
                        if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClassForPlayer2.itemGameAreaController.moveBlockLeft();
                            drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                        }
                    }
                    else {
                        //아이템 모드
                        if (tempClassForPlayer2.itemGameAreaController.ga.block.shape[0][0] == 9) {
                            if (tempClassForPlayer2.getChew()) {
                            } else {
                                tempClassForPlayer2.itemGameAreaController.moveBlockLeft();
                                drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                            }
                        } else {
                            if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                            } else {
                                tempClassForPlayer2.itemGameAreaController.moveBlockLeft();
                                drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                            }
                        }
                    }

                }
                else if (pressedKey == keySettingMapForPlayer2.get("rotate")) {
                    if (settingForPlayer1.getGameMode() == NormalMode || settingForPlayer1.getGameMode()==TimerMode) {
                        //일반모드 혹은 타이머 모드
                        if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClassForPlayer2.itemGameAreaController.rotateBlock();
                            drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                        }
                    } 
                    else {
                        //아이템모드
                        if (tempClassForPlayer2.itemGameAreaController.ga.block.shape[0][0] == 9) {
                        } else {
                            if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                            } else {
                                tempClassForPlayer2.itemGameAreaController.rotateBlock();
                                drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                            }

                        }
                    }
                }
                else if (pressedKey == keySettingMapForPlayer2.get("right")) {
                    if (settingForPlayer1.getGameMode() == NormalMode || settingForPlayer1.getGameMode()==TimerMode) {
                        //일반전 혹은 타이머 모드
                        if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClassForPlayer2.itemGameAreaController.moveBlockRight();
                            drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                        }
                    }
                    else {
                        //타이머 모드
                        if (tempClassForPlayer2.itemGameAreaController.ga.block.shape[0][0] == 9) {
                            if (tempClassForPlayer2.getChew()) {
                            } else {
                                tempClassForPlayer2.itemGameAreaController.moveBlockRight();
                                drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                            }
                        } else {
                            if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                            } else {
                                tempClassForPlayer2.itemGameAreaController.moveBlockRight();
                                drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                            }
                        }
                    }
                }
                else if (pressedKey == keySettingMapForPlayer2.get("down")) {
                    if (settingForPlayer1.getGameMode() == NormalMode || settingForPlayer1.getGameMode()==TimerMode) {
                        //일반 모드 혹은 타이머 모드
                        if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClassForPlayer2.itemGameAreaController.moveBlockDown();
                            drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                        }
                    }
                    else {
                        //아이템모드
                        if (tempClassForPlayer2.itemGameAreaController.ga.block == null || tempClassForPlayer2.getChew()) {
                        } else if (tempClassForPlayer2.itemGameAreaController.ga.block.shape[0][0] == 9){
                            tempClassForPlayer2.itemGameAreaController.moveBlockDown2();
                            drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                        }
                        else{
                            tempClassForPlayer2.itemGameAreaController.moveBlockDown();
                            drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                        }
                    }
                }

            }
        });
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
        gameBoardPaneArray[index-1].setText("");
        //여기서부턴 화면에 그리기


        drawTextWithColor(gameBoardPaneArray[index-1], "XXXXXXXXXXXX\n", Color.BLACK);


        for (int i = 0; i < 20; i++) {
            drawTextWithColor(gameBoardPaneArray[index-1], "X", Color.BLACK);

            for (int j = 0; j < 10; j++) {
                //일반 블럭 그리기
                if (background[i][j] < 8) {
                    if (settingForPlayer1.getDisplayMode() ==NotBlindMode ) { //일반모드 그리기
                        drawTextWithColor(gameBoardPaneArray[index-1], "X", colorForBlock[background[i][j]]);
                    } else { // 색맹모드 그리기
                        drawTextWithColor(gameBoardPaneArray[index-1], "X", colorFOrBlindModeBlock[background[i][j]]);
                    }
                }
                else {
                    if (settingForPlayer1.getDisplayMode() == NotBlindMode) { //일반모드 그리기
                        drawTextWithColor(gameBoardPaneArray[index-1], String.valueOf(blockShape[background[i][j]]), colorForBlock[background[i][j]]);
                    } else { // 색맹모드 그리기
                        drawTextWithColor(gameBoardPaneArray[index-1], String.valueOf(blockShape[background[i][j]]), colorFOrBlindModeBlock[background[i][j]]);
                    }
                }

            }
            drawTextWithColor(gameBoardPaneArray[index-1], "X\n", Color.BLACK);
        }


        drawTextWithColor(gameBoardPaneArray[index-1], "XXXXXXXXXXXX", Color.BLACK);

        //이거 없어도 보드는 그려진다. 뭔가 스타일 관련 코드인 듯
        StyledDocument doc = gameBoardPaneArray[index-1].getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        gameBoardPaneArray[index-1].setStyledDocument(doc);

    }


    private void drawNextBlock(int[][] background, int index) {

        //이전 화면 지우기
        nextBlockPaneArray[index-1].setText("");


        //여기서부턴 화면에 그리기
        //민재 형 이거 만들어 줘
        int nextBlock[][] = background;

        for (int i = 0; i < nextBlock.length; i++) {

            for (int j = 0; j < nextBlock[i].length; j++) {
                if(settingForPlayer1.getDisplayMode()==NotBlindMode) drawTextWithColor(nextBlockPaneArray[index-1], String.valueOf(blockShape[background[i][j]]), colorForBlock[background[i][j]]); //일반 모드
                else drawTextWithColor(nextBlockPaneArray[index-1], String.valueOf(blockShape[background[i][j]]), colorFOrBlindModeBlock[background[i][j]]); //색맹 모드
            }

            drawTextWithColor(nextBlockPaneArray[index-1], "\n", Color.BLACK);
        }
        //이거 없어도 보드는 그려진다. 뭔가 스타일 관련 코드인 듯
        StyledDocument doc = nextBlockPaneArray[index-1].getStyledDocument();


        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        nextBlockPaneArray[index-1].setStyledDocument(doc);

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
    private void Debug(int[][] rowsToSend) {
        if (rowsToSend == null) {
            System.out.println("null");
        } else {
            for (int i = 0; i < rowsToSend.length; i++) {
                for (int j = 0; j < 10; j++) {
                    System.out.print(rowsToSend[i][j]);
                }
                System.out.println();
            }
        }
    }
}
