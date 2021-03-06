package view;

import controller.HashMapParser;
import controller.PageController;
import model.setting.JsonSetting;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
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
    private JTextPane passedBlockPaneArray[]={passedBlockPane1,passedBlockPane2};

    private PageController pageController;

    private final int ItemMode=1;
    private final int NormalMode=0;
    private final int TimerMode=2;
    private final int BlindMode=1;
    private final int NotBlindMode=0;


    public boolean receive1;
    public int[][] toSend1={};
    public int received1;

    public boolean receive2;
    public int[][] toSend2={};
    public int received2;


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

    private VersusGamePageKeyListener versusGamePageKeyListener;

    public VersusGamePage() {
        //?????????
        initialize();
        //????????? ????????? ?????? ??????
        setKeyEventController();
        //?????? ????????? ?????? ?????? ??????
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

        //?????? ?????? ?????? ?????????
        gameBoardPane1.setMargin(new Insets(150, 0, 0, 0));
        nextBlockPane1.setMargin(new Insets(300, 0, 0, 0));
        //?????? ?????? ?????? ?????????
        gameBoardPane2.setMargin(new Insets(150, 0, 0, 0));
        nextBlockPane2.setMargin(new Insets(300, 0, 0, 0));

        passedBlockPane1.setMargin(new Insets(150, 0, 0, 0));
        passedBlockPane2.setMargin(new Insets(150, 0, 0, 0));

        gameBoardPane1.setEditable(true);
        gameBoardPane2.setEditable(true);

        this.add(mainPanel);


        gameBoardPane1.setEditable(true);
        gameBoardPane2.setEditable(true);

        this.add(mainPanel);
        //?????? ????????????

        //?????? label ?????????
        if (settingForPlayer1.getGameMode()==NormalMode || settingForPlayer1.getGameMode()==TimerMode) {//????????? ?????? ????????? ??????
            scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer1.getDelay()));
            scoreLabel2.setText(Integer.toString(tempClassForPlayer2.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer2.getDelay()));
        }
        else {//????????????
            scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "   Delay:  " + Integer.toString((int) tempClassForPlayer1.getDelay()) +
                    "   DoblueScore(Left):  " + Integer.toString(tempClassForPlayer1.getDoubleIndex()));
        scoreLabel1.setText(Integer.toString(tempClassForPlayer2.getScore()) + "   Delay:  " + Integer.toString((int) tempClassForPlayer2.getDelay()) +
                    "   DoblueScore(Left):  " + Integer.toString(tempClassForPlayer2.getDoubleIndex()));
        }

        //????????? ?????? ????????? ?????????
        if(settingForPlayer1.getGameMode()==TimerMode)
        {
            timeLabel.setText(Double.toString(limitTime));
        }
        //??????????????? ?????????????????? ????????? ?????? ????????????
        else{
            timeNameLabel.setText("");
            timeLabel.setText("");
        }


        //?????? ?????? ??????
        this.setSize(1000, 1000);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x??? ????????? ??? ???????????? ???????????? ??????

        //????????? ??? ??? ????????????
        HashMapParser hashmapparser = new HashMapParser();
        keySettingMapForPlayer1 = settingForPlayer1.getKeyList();
        keySettingMapForPlayer2 = settingForPlayer2.getKeyList();

        isStop = false;

        //???????????? ??? ????????? ????????? ??? ????????? ?????? ??????
        requestFocus();
        setFocusable(true);


        //?????? ???????????? ??????
        this.setLocationRelativeTo(null);
        this.receive1=false;
        this.receive2=false;

    }

    private void setTimer()//???????????? ?????????
    {
        timerForPlyer1 = new Timer((int) tempClassForPlayer1.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                requestFocus();
                setFocusable(true);



                tempClassForPlayer1.clearLinesInTempClass();
                //?????? ?????? ?????? ?????? ?????? ??? ?????? ??? ??????.
                //?????? ??????
                tempClassForPlayer1.itemGameAreaController.moveBlockDown();



                drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);



                if (!tempClassForPlayer1.itemGameAreaController.checkBottom()) {
                    if (tempClassForPlayer1.itemGameAreaController.isBlockOuOofBounds()) {
                        //?????? ?????????
                        timerForPlyer1.stop();
                        timerForPlyer2.stop();

                        dispose();
                        pageController = new PageController();
                        // player 1??? ??????.
                        pageController.setWinner(2);
                        pageController.createVersusGameEndPage();
                    }
                    else {
                        int[][] rowsToSend = tempClassForPlayer1.itemGameAreaController.rowsToSend();
                        if(rowsToSend!=null){
                            toSend2=rowsToSend;
                            receive2=true;
                            rowsToSend=null;
                        }
                        tempClassForPlayer1.clearLinesWhenBlockIsBottomButNotEnd();
                        if(receive1){
                            if(received1>10){
                                receive1=false;
                                toSend1=null;
                            }
                            else if(received1+toSend1.length>10){
                                int a = 10-received1;
                                int[][] k= new int[a][10];
                                for(int i=0; i<a;i++){
                                    k[i]=toSend1[a-i];
                                }
                                if(!tempClassForPlayer1.itemGameAreaController.receive(k))
                                {
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();
                                    pageController = new PageController();
                                    // player 1??? ??????.
                                    pageController.setWinner(2);
                                    pageController.createVersusGameEndPage();
                                }
                                else {
                                    receive1 = false;
                                    toSend1 = null;
                                    received1 = 11;
                                }
                            }
                            else {

                                if(!tempClassForPlayer1.itemGameAreaController.receive(toSend1))
                                {
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();

                                    dispose();
                                    pageController = new PageController();
                                    // player 1??? ??????.
                                    pageController.setWinner(2);
                                    pageController.createVersusGameEndPage();
                                }
                                else {
                                    receive1 = false;
                                    received1 += toSend1.length;
                                    toSend1 = null;
                                }
                            }
                        }

                        timerForPlyer1.setDelay((int) tempClassForPlayer1.getDelay());
                        drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                    }

                }

                //????????? ?????? ??????
                scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer1.getDelay()));

                //?????? ?????? ?????????
                drawNextBlock(tempClassForPlayer1.getNextBlock(),1);

                drawPassedBlock(toSend1,1);

            }

        });

        timerForPlyer2 = new Timer((int) tempClassForPlayer2.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                requestFocus();
                setFocusable(true);



                tempClassForPlayer2.clearLinesInTempClass();

                //?????? ??????
                tempClassForPlayer2.itemGameAreaController.moveBlockDown();



                drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);



                if (!tempClassForPlayer2.itemGameAreaController.checkBottom()) {
                    if (tempClassForPlayer2.itemGameAreaController.isBlockOuOofBounds()) {
                        //?????? ?????????
                        timerForPlyer1.stop();
                        timerForPlyer2.stop();
                        dispose();
                        pageController = new PageController();
                        //????????????2??? ??????
                        pageController.setWinner(1);
                        pageController.createVersusGameEndPage();
                    }
                    else {
                        int[][] rowsToSend = tempClassForPlayer2.itemGameAreaController.rowsToSend();
                        if(rowsToSend!=null){
                            toSend1=rowsToSend;
                            receive1=true;
                            rowsToSend=null;
                        }
                        tempClassForPlayer2.clearLinesWhenBlockIsBottomButNotEnd();
                        if(receive2){
                            if(received2>10){
                                receive2=false;
                                toSend2=null;
                            }
                            else if(received2+toSend2.length>10){
                                int a = 10-received2;
                                int[][] k= new int[a][10];
                                for(int i=0; i<a;i++){
                                    k[i]=toSend2[a-i];
                                }
                                if(!tempClassForPlayer2.itemGameAreaController.receive(k)){
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();
                                    pageController = new PageController();
                                    //????????????2??? ??????
                                    pageController.setWinner(1);
                                    pageController.createVersusGameEndPage();
                                }
                                else {
                                    receive2 = false;
                                    toSend2 = null;
                                    received2 = 11;
                                }
                            }
                            else {
                                if(!tempClassForPlayer2.itemGameAreaController.receive(toSend2)){
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();
                                    pageController = new PageController();
                                    //????????????2??? ??????
                                    pageController.setWinner(1);
                                    pageController.createVersusGameEndPage();
                                }
                                else {
                                    receive2 = false;
                                    received2 += toSend2.length;
                                    toSend2 = null;
                                }
                            }
                        }
                        timerForPlyer2.setDelay((int) tempClassForPlayer2.getDelay());
                        drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                    }

                }

                //????????? ?????? ??????
                scoreLabel2.setText(Integer.toString(tempClassForPlayer2.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer2.getDelay()));

                //?????? ?????? ?????????
                drawNextBlock(tempClassForPlayer2.getNextBlock(),2);
                drawPassedBlock(toSend2,2);

            }

        });

        timerForPlyer1.start();
        timerForPlyer2.start();
    }


    private void setTimer2() //????????? ????????? ????????? ??????
    {
        timerForPlyer1 = new Timer((int) tempClassForPlayer1.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                requestFocus();
                setFocusable(true);
                if (tempClassForPlayer1.itemGameAreaController.ga.block == null) { //????????????
                }
                else if (tempClassForPlayer1.itemGameAreaController.ga.block.shape[0][0] == 9) { //????????? ???????????? ??????
                    if(tempClassForPlayer1.func2()) {
                        if (receive1) {
                            if (received1 > 10) {
                                receive1 = false;
                                toSend1 = null;
                            } else if (received1 + toSend1.length > 10) {
                                int a = 10 - received1;
                                int[][] k = new int[a][10];
                                for (int i = 0; i < a; i++) {
                                    k[i] = toSend1[a-i];
                                }
                                if (!tempClassForPlayer1.itemGameAreaController.receive(k)) {
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();

                                    //player1??? ??????
                                    pageController = new PageController();
                                    pageController.setWinner(2);
                                    pageController.createVersusGameEndPage();
                                } else {
                                    receive1 = false;
                                    toSend1 = null;
                                    received1 = 11;
                                }
                            } else {
                                if (!tempClassForPlayer1.itemGameAreaController.receive(toSend1)) {
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();

                                    //player1??? ??????
                                    pageController = new PageController();
                                    pageController.setWinner(2);
                                    pageController.createVersusGameEndPage();
                                } else {
                                    receive1 = false;
                                    received1 += toSend1.length;
                                    toSend1 = null;
                                }
                            }
                        }
                    }
                    drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                    drawPassedBlock(toSend1,1);
                }

                else {
                    tempClassForPlayer1.clearLinesInTempClass(); //?????? ?????????

                    tempClassForPlayer1.itemGameAreaController.moveBlockDown(); //?????? 1??? ????????????



                    drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1); //??????



                    if (!tempClassForPlayer1.itemGameAreaController.checkBottom()) { //????????? ????????????
                        if (tempClassForPlayer1.itemGameAreaController.isBlockOuOofBounds()) {
                            //?????? ??????
                            // (????????????)
                            timerForPlyer1.stop();
                            timerForPlyer2.stop();
                            dispose();

                            //player1??? ??????
                            pageController = new PageController();
                            pageController.setWinner(2);
                            pageController.createVersusGameEndPage();
                        }



                        else {
                            int[][] rowsToSend = tempClassForPlayer1.itemGameAreaController.rowsToSend();
                            if(rowsToSend!=null){
                                toSend2=rowsToSend;
                                receive2=true;
                                rowsToSend=null;
                            }
                            tempClassForPlayer1.func1(); //?????? ?????????
                            if(receive1){
                                if(received1>10){
                                    receive1=false;
                                    toSend1=null;
                                }
                                else if(received1+toSend1.length>10){
                                    int a = 10-received1;
                                    int[][] k= new int[a][10];
                                    for(int i=0; i<a;i++){
                                        k[i]=toSend1[a-i];
                                    }
                                    if(!tempClassForPlayer1.itemGameAreaController.receive(k)){
                                        timerForPlyer1.stop();
                                        timerForPlyer2.stop();
                                        dispose();

                                        //player1??? ??????
                                        pageController = new PageController();
                                        pageController.setWinner(2);
                                        pageController.createVersusGameEndPage();
                                    }
                                    else {
                                        receive1 = false;
                                        toSend1 = null;
                                        received1 = 11;
                                    }
                                }
                                else {
                                    if(!tempClassForPlayer1.itemGameAreaController.receive(toSend1))
                                    {
                                        timerForPlyer1.stop();
                                        timerForPlyer2.stop();
                                        dispose();

                                        //player1??? ??????
                                        pageController = new PageController();
                                        pageController.setWinner(2);
                                        pageController.createVersusGameEndPage();
                                    }
                                    else {
                                        receive1 = false;
                                        received1 += toSend1.length;
                                        toSend1 = null;
                                    }
                                }
                            }

                            timerForPlyer1.setDelay((int) tempClassForPlayer1.getDelay());
                            drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                        }
                    }

                    //?????? ???, ?????? ?????? ?????? ?????? ?????? ???

                    //????????? ?????? ??????
                    scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "   Delay:  " + Integer.toString((int) tempClassForPlayer1.getDelay()) + "   DoblueScore(Left):  " + Integer.toString(tempClassForPlayer1.getDoubleIndex()));
                    //?????? ?????? ?????????
                    drawNextBlock(tempClassForPlayer1.getNextBlock(),1);
                    drawPassedBlock(toSend1,1);
                }
            }

        });
        timerForPlyer2 = new Timer((int) tempClassForPlayer2.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                requestFocus();
                setFocusable(true);
                if (tempClassForPlayer2.itemGameAreaController.ga.block == null) { //????????????
                }
                else if (tempClassForPlayer2.itemGameAreaController.ga.block.shape[0][0] == 9) { //????????? ???????????? ??????
                    if(tempClassForPlayer2.func2())
                    {
                        if(receive2) {
                            if (received2 > 10) {
                                receive2 = false;
                                toSend2 = null;
                            } else if (received2 + toSend2.length > 10) {
                                int a = 10 - received2;
                                int[][] k = new int[a][10];
                                for (int i = 0; i < a; i++) {
                                    k[i] = toSend2[a-i];
                                }
                                if (!tempClassForPlayer2.itemGameAreaController.receive(k)) {
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();

                                    pageController = new PageController();
                                    //player2??? ??????
                                    pageController.setWinner(1);
                                    pageController.createVersusGameEndPage();
                                } else {
                                    receive2 = false;
                                    toSend2 = null;
                                    received2 = 11;
                                }
                            } else {
                                if (!tempClassForPlayer2.itemGameAreaController.receive(toSend2)) {
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();

                                    pageController = new PageController();
                                    //player2??? ??????
                                    pageController.setWinner(1);
                                    pageController.createVersusGameEndPage();
                                } else {
                                    receive2 = false;
                                    received2 += toSend2.length;
                                    toSend2 = null;
                                }
                            }
                        }
                    }
                    drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                    drawPassedBlock(toSend2,2);
                }
                else {

                    tempClassForPlayer2.clearLinesInTempClass(); //?????? ?????????
                    tempClassForPlayer2.itemGameAreaController.moveBlockDown(); //?????? 1??? ????????????



                    drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2); //??????



                    if (!tempClassForPlayer2.itemGameAreaController.checkBottom()) { //????????? ????????????
                        if (tempClassForPlayer2.itemGameAreaController.isBlockOuOofBounds()) {
                            //?????? ??????
                            // (????????????)
                            timerForPlyer1.stop();
                            timerForPlyer2.stop();
                            dispose();

                            pageController = new PageController();
                            //player2??? ??????
                            pageController.setWinner(1);
                            pageController.createVersusGameEndPage();
                        }



                        else {
                            int[][] rowsToSend = tempClassForPlayer2.itemGameAreaController.rowsToSend();
                            if(rowsToSend!=null){
                                toSend1=rowsToSend;
                                receive1=true;
                                rowsToSend=null;
                            }
                            tempClassForPlayer2.func1(); //?????? ?????????
                            if(receive2){
                                if(received2>10){
                                    receive2=false;
                                    toSend2=null;
                                }
                                else if(received2+toSend2.length>10){
                                    int a = 10-received2;
                                    int[][] k= new int[a][10];
                                    for(int i=0; i<a;i++){
                                        k[i]=toSend2[a-i];
                                    }
                                    if(!tempClassForPlayer2.itemGameAreaController.receive(k)){
                                        timerForPlyer1.stop();
                                        timerForPlyer2.stop();
                                        dispose();

                                        pageController = new PageController();
                                        //player2??? ??????
                                        pageController.setWinner(1);
                                        pageController.createVersusGameEndPage();
                                    }
                                    else{
                                        receive2 = false;
                                        toSend2 = null;
                                        received2 = 11;
                                    }
                                }
                                else {
                                    if(!tempClassForPlayer2.itemGameAreaController.receive(toSend2)) {
                                        timerForPlyer1.stop();
                                        timerForPlyer2.stop();
                                        dispose();

                                        pageController = new PageController();
                                        //player2??? ??????
                                        pageController.setWinner(1);
                                        pageController.createVersusGameEndPage();
                                    }
                                    else {
                                        receive2 = false;
                                        received2 += toSend2.length;
                                        toSend2 = null;
                                    }
                                }
                            }

                            timerForPlyer2.setDelay((int) tempClassForPlayer2.getDelay());
                            drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                        }
                    }

                    //?????? ???, ?????? ?????? ?????? ?????? ?????? ???

                    //????????? ?????? ??????
                    scoreLabel2.setText(Integer.toString(tempClassForPlayer2.getScore()) + "   Delay:  " + Integer.toString((int) tempClassForPlayer2.getDelay()) + "   DoblueScore(Left):  " + Integer.toString(tempClassForPlayer2.getDoubleIndex()));
                    //?????? ?????? ?????????
                    drawNextBlock(tempClassForPlayer2.getNextBlock(),2);
                    drawPassedBlock(toSend2,2);
                }
            }

        });

        timerForPlyer1.start();
        timerForPlyer2.start();
    }
    private void setTimer3() //????????? ????????? ?????????
    {
        timerForPlyer1 = new Timer((int) tempClassForPlayer1.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                requestFocus();
                setFocusable(true);

                //???????????? ?????????
                limitTime-=(tempClassForPlayer1.getDelay()/1000);
                timeLabel.setText(Double.toString(limitTime));

                //??????????????? 0????????? ?????? ??????
                if(limitTime<0)
                {
                    timerForPlyer1.stop();
                    timerForPlyer2.stop();
                    dispose();
                    pageController = new PageController();
                    //player1??? ??????.
                    pageController.setWinner(tempClassForPlayer1.getScore()>= tempClassForPlayer2.getScore()?1:2);
                    pageController.createVersusGameEndPage();
                }


                tempClassForPlayer1.clearLinesInTempClass();
                //?????? ??????

                tempClassForPlayer1.itemGameAreaController.moveBlockDown();



                drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);



                if (!tempClassForPlayer1.itemGameAreaController.checkBottom()) {
                    if (tempClassForPlayer1.itemGameAreaController.isBlockOuOofBounds()) {
                        //?????? ?????????
                        timerForPlyer1.stop();
                        timerForPlyer2.stop();
                        dispose();
                        pageController = new PageController();
                        //player1??? ??????.
                        pageController.setWinner(2);
                        pageController.createVersusGameEndPage();
                    }
                    else {
                        int[][] rowsToSend = tempClassForPlayer1.itemGameAreaController.rowsToSend();
                        if(rowsToSend!=null){
                            toSend2=rowsToSend;
                            receive2=true;
                            rowsToSend=null;
                        }
                        tempClassForPlayer1.clearLinesWhenBlockIsBottomButNotEnd();
                        if(receive1){
                            if(received1>10){
                                receive1=false;
                                toSend1=null;
                            }
                            else if(received1+toSend1.length>10){
                                int a = 10-received1;
                                int[][] k= new int[a][10];
                                for(int i=0; i<a;i++){
                                    k[i]=toSend1[a-i];
                                }
                                if(!tempClassForPlayer1.itemGameAreaController.receive(k))
                                {
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();
                                    pageController = new PageController();
                                    //player1??? ??????.
                                    pageController.setWinner(2);
                                    pageController.createVersusGameEndPage();
                                }
                                else {
                                    receive1 = false;
                                    toSend1 = null;
                                    received1 = 11;
                                }
                            }
                            else {
                                if(!tempClassForPlayer1.itemGameAreaController.receive(toSend1))
                                {
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();
                                    pageController = new PageController();
                                    //player1??? ??????.
                                    pageController.setWinner(2);
                                    pageController.createVersusGameEndPage();
                                }
                                else {
                                    receive1 = false;
                                    received1 += toSend1.length;
                                    toSend1 = null;
                                }
                            }
                        }

                        timerForPlyer1.setDelay((int) tempClassForPlayer1.getDelay());
                        drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                    }

                }

                //????????? ?????? ??????
                scoreLabel1.setText(Integer.toString(tempClassForPlayer1.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer1.getDelay()));

                //?????? ?????? ?????????
                drawNextBlock(tempClassForPlayer1.getNextBlock(),1);
                drawPassedBlock(toSend1,1);
            }

        });

        timerForPlyer2 = new Timer((int) tempClassForPlayer2.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                requestFocus();
                setFocusable(true);



                tempClassForPlayer2.clearLinesInTempClass();

                tempClassForPlayer2.itemGameAreaController.moveBlockDown();



                drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);



                if (!tempClassForPlayer2.itemGameAreaController.checkBottom()) {
                    if (tempClassForPlayer2.itemGameAreaController.isBlockOuOofBounds()) {
                        //?????? ?????????
                        timerForPlyer1.stop();
                        timerForPlyer2.stop();
                        dispose();
                        pageController = new PageController();
                        //player2??? ??????
                        pageController.setWinner(1);
                        pageController.createVersusGameEndPage();
                    }
                    else {
                        int[][] rowsToSend = tempClassForPlayer2.itemGameAreaController.rowsToSend();
                        if(rowsToSend!=null){
                            toSend1=rowsToSend;
                            receive1=true;
                            rowsToSend=null;
                        }
                        tempClassForPlayer2.clearLinesWhenBlockIsBottomButNotEnd();
                        if(receive2){
                            if(received2>10){
                                receive2=false;
                                toSend2=null;
                            }
                            else if(received2+toSend2.length>10){
                                int a = 10-received2;
                                int[][] k= new int[a][10];
                                for(int i=0; i<a;i++){
                                    k[i]=toSend2[a-i];
                                }

                                if(!tempClassForPlayer2.itemGameAreaController.receive(k)) {
                                    //?????? ?????????
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();
                                    pageController = new PageController();
                                    //player2??? ??????
                                    pageController.setWinner(1);
                                    pageController.createVersusGameEndPage();
                                }
                                else {
                                    receive2 = false;
                                    toSend2 = null;
                                    received2 = 11;
                                }
                            }
                            else {
                                if(!tempClassForPlayer2.itemGameAreaController.receive(toSend2)){
                                    //?????? ?????????
                                    timerForPlyer1.stop();
                                    timerForPlyer2.stop();
                                    dispose();
                                    pageController = new PageController();
                                    //player2??? ??????
                                    pageController.setWinner(1);
                                    pageController.createVersusGameEndPage();
                                }
                                else {
                                    receive2 = false;
                                    received2 += toSend2.length;
                                    toSend2 = null;
                                }
                            }
                        }
                        timerForPlyer2.setDelay((int) tempClassForPlayer2.getDelay());
                        drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                    }

                }

                //????????? ?????? ??????
                scoreLabel2.setText(Integer.toString(tempClassForPlayer2.getScore()) + "delay:" + Integer.toString((int) tempClassForPlayer2.getDelay()));

                //?????? ?????? ?????????
                drawNextBlock(tempClassForPlayer2.getNextBlock(),2);
                drawPassedBlock(toSend2,2);
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
        versusGamePageKeyListener=new VersusGamePageKeyListener();
        addKeyListener(versusGamePageKeyListener);
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


    public void drawGameBoard(int[][] background, int playerIndex) {

        //?????? ?????? ?????????
        gameBoardPaneArray[playerIndex-1].setText("");
        //??????????????? ????????? ?????????


        drawTextWithColor(gameBoardPaneArray[playerIndex-1], "XXXXXXXXXXXX\n", Color.BLACK);


        for (int i = 0; i < 20; i++) {
            drawTextWithColor(gameBoardPaneArray[playerIndex-1], "X", Color.BLACK);

            for (int j = 0; j < 10; j++) {
                //?????? ?????? ?????????
                if (background[i][j] < 8) {
                    if (settingForPlayer1.getDisplayMode() ==NotBlindMode ) { //???????????? ?????????
                        drawTextWithColor(gameBoardPaneArray[playerIndex-1], "X", colorForBlock[background[i][j]]);
                    } else { // ???????????? ?????????
                        drawTextWithColor(gameBoardPaneArray[playerIndex-1], "X", colorFOrBlindModeBlock[background[i][j]]);
                    }
                }
                else {
                    if (settingForPlayer1.getDisplayMode() == NotBlindMode) { //???????????? ?????????
                        drawTextWithColor(gameBoardPaneArray[playerIndex-1], String.valueOf(blockShape[background[i][j]]), colorForBlock[background[i][j]]);
                    } else { // ???????????? ?????????
                        drawTextWithColor(gameBoardPaneArray[playerIndex-1], String.valueOf(blockShape[background[i][j]]), colorFOrBlindModeBlock[background[i][j]]);
                    }
                }

            }
            drawTextWithColor(gameBoardPaneArray[playerIndex-1], "X\n", Color.BLACK);
        }


        drawTextWithColor(gameBoardPaneArray[playerIndex-1], "XXXXXXXXXXXX", Color.BLACK);

        //?????? ????????? ????????? ????????????. ?????? ????????? ?????? ????????? ???
        StyledDocument doc = gameBoardPaneArray[playerIndex-1].getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        gameBoardPaneArray[playerIndex-1].setStyledDocument(doc);

    }


    private void drawNextBlock(int[][] background, int playerIndex) {

        //?????? ?????? ?????????
        nextBlockPaneArray[playerIndex-1].setText("");


        //??????????????? ????????? ?????????
        //?????? ??? ?????? ????????? ???
        int nextBlock[][] = background;

        for (int i = 0; i < nextBlock.length; i++) {

            for (int j = 0; j < nextBlock[i].length; j++) {
                if(settingForPlayer1.getDisplayMode()==NotBlindMode)
                    drawTextWithColor(nextBlockPaneArray[playerIndex-1], String.valueOf(blockShape[background[i][j]]), colorForBlock[background[i][j]]); //?????? ??????
                else
                    drawTextWithColor(nextBlockPaneArray[playerIndex-1], String.valueOf(blockShape[background[i][j]]), colorFOrBlindModeBlock[background[i][j]]); //?????? ??????
            }

            drawTextWithColor(nextBlockPaneArray[playerIndex-1], "\n", Color.BLACK);
        }
        //?????? ????????? ????????? ????????????. ?????? ????????? ?????? ????????? ???
        StyledDocument doc = nextBlockPaneArray[playerIndex-1].getStyledDocument();


        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        nextBlockPaneArray[playerIndex-1].setStyledDocument(doc);

    }

    private void drawPassedBlock(int [][] passedBlock, int playerIndex)
    {
        passedBlockPaneArray[playerIndex-1].setText("");

        if(passedBlock==null)
            return;

        for(int i=0;i<passedBlock.length;i++){
            for(int j=0;j<passedBlock[i].length;j++){
                if(passedBlock[i][j]>0)
                    drawTextWithColor(passedBlockPaneArray[playerIndex-1],String.valueOf("X"),Color.GRAY);
                else
                    drawTextWithColor(passedBlockPaneArray[playerIndex-1],String.valueOf("X"),Color.WHITE);
            }
            drawTextWithColor(passedBlockPaneArray[playerIndex-1], "\n", Color.BLACK);
        }
        StyledDocument doc = passedBlockPaneArray[playerIndex-1].getStyledDocument();


        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        passedBlockPaneArray[playerIndex-1].setStyledDocument(doc);
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

    public class VersusGamePageKeyListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {


        }
        @Override
        public void keyPressed(KeyEvent e) { //??? ????????????
            // TODO Auto-generated method stub
            //?????? switch case?????????, case??? constant ?????? ????????? ??? ????????? if??? ??????
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
                    // ????????????, ????????? ??????
                    if (tempClassForPlayer1.itemGameAreaController.ga.block == null || tempClassForPlayer1.getEffect()) {
                    } else {

                        tempClassForPlayer1.itemGameAreaController.dropBlock();
                        drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                    }
                }
                else {
                    //????????????
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
                    //????????? ?????? ?????????
                    if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClassForPlayer1.itemGameAreaController.moveBlockLeft();
                        drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                    }
                }
                else {
                    //????????????
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
                    //???????????? ?????? ???????????????
                    if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClassForPlayer1.itemGameAreaController.rotateBlock();
                        drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                    }
                }
                else {
                    //????????????
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
                    //???????????? ?????? ???????????????
                    if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClassForPlayer1.itemGameAreaController.moveBlockRight();
                        drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                    }
                }
                else {
                    //????????????
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
                    //???????????? ?????? ???????????????
                    if (tempClassForPlayer1.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClassForPlayer1.itemGameAreaController.moveBlockDown();
                        drawGameBoard(tempClassForPlayer1.itemGameAreaController.newBackground(),1);
                    }
                }
                else {
                    //???????????????
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
                    //????????? ?????? ????????? ??????
                    if (tempClassForPlayer2.itemGameAreaController.ga.block == null || tempClassForPlayer2.getEffect()) {
                    } else {

                        tempClassForPlayer2.itemGameAreaController.dropBlock();
                        drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                    }
                }
                else {
                    //????????????
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
                    //???????????? ?????? ???????????????
                    if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClassForPlayer2.itemGameAreaController.moveBlockLeft();
                        drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                    }
                }
                else {
                    //????????? ??????
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
                    //???????????? ?????? ????????? ??????
                    if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClassForPlayer2.itemGameAreaController.rotateBlock();
                        drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                    }
                }
                else {
                    //???????????????
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
                    //????????? ?????? ????????? ??????
                    if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClassForPlayer2.itemGameAreaController.moveBlockRight();
                        drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                    }
                }
                else {
                    //????????? ??????
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
                    //?????? ?????? ?????? ????????? ??????
                    if (tempClassForPlayer2.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClassForPlayer2.itemGameAreaController.moveBlockDown();
                        drawGameBoard(tempClassForPlayer2.itemGameAreaController.newBackground(),2);
                    }
                }
                else {
                    //???????????????
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
    }
}
