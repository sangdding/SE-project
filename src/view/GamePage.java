package view;

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
import java.awt.event.*;
import java.util.HashMap;
import controller.HashMapParser;
import java.util.Random;
public class GamePage extends JFrame {

    private final int ItemMode=1;
    private final int BlindMode=1;
    private final int NotBlindMode=0;


    private temp tempClass;



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
    private JLabel scoreLabel;
    private PageController pageController;
    private GamePageKeyListener gamePageKeyListener;


    public Timer timer;
    private boolean isStop;


    private JsonSetting setting = new JsonSetting("player1");
    private HashMap<String, Integer> keySettingMap;


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

    private int gameMode;


    public GamePage() {
        //?????????
        initialize();
        //????????? ????????? ?????? ??????
        setKeyEventController();
        //?????? ????????? ?????? ?????? ??????
        setButtonClickController();
        drawGameBoard(tempClass.getBackground());
        if(setting.getGameMode()==ItemMode)
        {
            setTimer2();
        }
        else
        {
            setTimer();
        }
    }

    private void initialize() {

        gameMode = setting.getGameMode();

        //?????????
        setStyleDocument();



        tempClass = new temp();

        //?????? ?????? ?????? ?????????
        gameBoardPane.setMargin(new Insets(150, 0, 0, 0));
        nextBlockPane.setMargin(new Insets(300, 0, 0, 0));

        gameBoardPane.setEditable(true);

        this.add(mainPanel);

        //?????? ????????????

        //?????? label ??????
        if (gameMode==0) scoreLabel.setText(Integer.toString(tempClass.getScore()) + "delay:" + Integer.toString((int) tempClass.getDelay()));//?????????
        else scoreLabel.setText(Integer.toString(tempClass.getScore()) + "   Delay:  " + Integer.toString((int) tempClass.getDelay()) +
                "   DoblueScore(Left):  " + Integer.toString(tempClass.getDoubleIndex())); // ????????????


        //?????? ?????? ??????
        HashMap<String, Integer> settingMap = setting.getDisplaySize();
        this.setSize(settingMap.get("width"), settingMap.get("height"));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x??? ????????? ??? ???????????? ???????????? ??????



        //????????? ??? ??? ????????????
        HashMapParser hashmapparser = new HashMapParser();
        keySettingMap = setting.getKeyList();


        isStop = false;

        //???????????? ??? ????????? ????????? ??? ????????? ?????? ??????
        requestFocus();
        setFocusable(true);


        //?????? ???????????? ??????
        this.setLocationRelativeTo(null);

    }


    private void setTimer() {
        timer = new Timer((int) tempClass.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                requestFocus();
                setFocusable(true);



                tempClass.clearLinesInTempClass();
                 //?????? ??????
                tempClass.itemGameAreaController.moveBlockDown();



                drawGameBoard(tempClass.itemGameAreaController.newBackground());



                if (!tempClass.itemGameAreaController.checkBottom()) {
                    if (tempClass.itemGameAreaController.isBlockOuOofBounds()) {
                        //?????? ?????????
                        timer.stop();
                        dispose();
                        pageController = new PageController();
                        pageController.setScore(tempClass.getScore());
                        pageController.createGameEndPage();
                    }
                    else {
                        tempClass.clearLinesWhenBlockIsBottomButNotEnd();
                        timer.setDelay((int) tempClass.getDelay());
                        drawGameBoard(tempClass.itemGameAreaController.newBackground());
                    }

                }

                //????????? ?????? ??????
                scoreLabel.setText(Integer.toString(tempClass.getScore()) + "delay:" + Integer.toString((int) tempClass.getDelay()));

                //?????? ?????? ?????????
                drawNextBlock(tempClass.getNextBlock());

            }

        });
        timer.start();
    }

    private void setTimer2() //????????? ????????? ????????? ??????
    {
        timer = new Timer((int) tempClass.getDelay(), new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                requestFocus();
                setFocusable(true);
                if (tempClass.itemGameAreaController.ga.block == null) { //????????????
                }
                else if (tempClass.itemGameAreaController.ga.block.shape[0][0] == 9) { //????????? ???????????? ??????
                    tempClass.func2();
                    drawGameBoard(tempClass.itemGameAreaController.newBackground());
                }
                else {
                    tempClass.clearLinesInTempClass(); //?????? ?????????
                    tempClass.itemGameAreaController.moveBlockDown(); //?????? 1??? ????????????



                    drawGameBoard(tempClass.itemGameAreaController.newBackground()); //??????



                    if (!tempClass.itemGameAreaController.checkBottom()) { //????????? ????????????
                        if (tempClass.itemGameAreaController.isBlockOuOofBounds()) {
                            //?????? ??????
                            // (????????????)
                            timer.stop();
                            dispose();

                            pageController = new PageController();
                            pageController.setScore(tempClass.getScore());
                            pageController.createGameEndPage();
                        }



                        else {
                            tempClass.func1(); //?????? ?????????
                            timer.setDelay((int) tempClass.getDelay());
                            drawGameBoard(tempClass.itemGameAreaController.newBackground());
                        }
                    }

                    //?????? ???, ?????? ?????? ?????? ?????? ?????? ???

                    //????????? ?????? ??????
                    scoreLabel.setText(Integer.toString(tempClass.getScore()) + "   Delay:  " + Integer.toString((int) tempClass.getDelay()) + "   DoblueScore(Left):  " + Integer.toString(tempClass.getDoubleIndex()));
                    //?????? ?????? ?????????
                    drawNextBlock(tempClass.getNextBlock());
                }
            }

        });

        timer.start();
    }

    private void setKeyEventController() {
        gamePageKeyListener=new GamePageKeyListener();
        addKeyListener(gamePageKeyListener);
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

    private void setStyleDocument()
    {
        styleSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet, 18);
        StyleConstants.setFontFamily(styleSet, "comic sans");
        StyleConstants.setBold(styleSet, false);
        StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);
    }

    public void drawGameBoard(int[][] background) {

        //?????? ?????? ?????????
        gameBoardPane.setText("");
        //??????????????? ????????? ?????????


        drawTextWithColor(gameBoardPane, "XXXXXXXXXXXX\n", Color.BLACK);


        for (int i = 0; i < 20; i++) {
            drawTextWithColor(gameBoardPane, "X", Color.BLACK);

            for (int j = 0; j < 10; j++) {
                //?????? ?????? ?????????
                if (background[i][j] < 8) {
                    if (setting.getDisplayMode() ==NotBlindMode ) { //???????????? ?????????
                        drawTextWithColor(gameBoardPane, "X", colorForBlock[background[i][j]]);
                    } else { // ???????????? ?????????
                        drawTextWithColor(gameBoardPane, "X", colorFOrBlindModeBlock[background[i][j]]);
                    }
                }
                else {
                    if (setting.getDisplayMode() == NotBlindMode) { //???????????? ?????????
                        drawTextWithColor(gameBoardPane, String.valueOf(blockShape[background[i][j]]), colorForBlock[background[i][j]]);
                    } else { // ???????????? ?????????
                        drawTextWithColor(gameBoardPane, String.valueOf(blockShape[background[i][j]]), colorFOrBlindModeBlock[background[i][j]]);
                    }
                }

            }
            drawTextWithColor(gameBoardPane, "X\n", Color.BLACK);
        }


        drawTextWithColor(gameBoardPane, "XXXXXXXXXXXX", Color.BLACK);

        //?????? ????????? ????????? ????????????. ?????? ????????? ?????? ????????? ???
        StyledDocument doc = gameBoardPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        gameBoardPane.setStyledDocument(doc);

    }


    private void drawNextBlock(int[][] background) {

        //?????? ?????? ?????????
        nextBlockPane.setText("");


        //??????????????? ????????? ?????????
        //?????? ??? ?????? ????????? ???
        int nextBlock[][] = background;

        for (int i = 0; i < nextBlock.length; i++) {
            for (int j = 0; j < nextBlock[0].length; j++) {
                if(setting.getDisplayMode()==NotBlindMode) drawTextWithColor(nextBlockPane, String.valueOf(blockShape[background[i][j]]), colorForBlock[background[i][j]]); //?????? ??????
                else drawTextWithColor(nextBlockPane, String.valueOf(blockShape[background[i][j]]), colorFOrBlindModeBlock[background[i][j]]); //?????? ??????
            }

            drawTextWithColor(nextBlockPane, "\n", Color.BLACK);
        }
        //?????? ????????? ????????? ????????????. ?????? ????????? ?????? ????????? ???
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

    public class GamePageKeyListener implements KeyListener{
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

            if (pressedKey == keySettingMap.get("resume")) {
                if (isStop) {
                    isStop = false;
                    timer.start();
                }
            }
            else if (pressedKey == keySettingMap.get("drop")) {
                if (setting.getGameMode() == 0) {
                    if (tempClass.itemGameAreaController.ga.block == null || tempClass.getEffect()) {
                    } else {

                        tempClass.itemGameAreaController.dropBlock();
                        drawGameBoard(tempClass.itemGameAreaController.newBackground());
                    }
                } else {
                    if (tempClass.itemGameAreaController.ga.block.shape[0][0] == 9) {
                        if (tempClass.getChew()) {
                        } else {
                            tempClass.itemGameAreaController.dropBlock();
                            drawGameBoard(tempClass.itemGameAreaController.newBackground());
                        }
                    } else {
                        if (tempClass.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClass.itemGameAreaController.dropBlock();
                            drawGameBoard(tempClass.itemGameAreaController.newBackground());
                        }
                    }
                }
            }
            else if (pressedKey == keySettingMap.get("exit")) {
                timer.stop();
                dispose();

                pageController = new PageController("Main");

            }
            else if ((setting.getGameMode() == 0 && tempClass.itemGameAreaController.ga.block == null) ||
                    (setting.getGameMode() == 1 && tempClass.itemGameAreaController.ga.block == null)) {
            }
            else if (pressedKey == keySettingMap.get("left")) {
                if (setting.getGameMode() == 0) {
                    if (tempClass.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClass.itemGameAreaController.moveBlockLeft();
                        drawGameBoard(tempClass.itemGameAreaController.newBackground());
                    }
                }
                else {
                    if (tempClass.itemGameAreaController.ga.block.shape[0][0] == 9) {
                        if (tempClass.getChew()) {
                        } else {
                            tempClass.itemGameAreaController.moveBlockLeft();
                            drawGameBoard(tempClass.itemGameAreaController.newBackground());
                        }
                    } else {
                        if (tempClass.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClass.itemGameAreaController.moveBlockLeft();
                            drawGameBoard(tempClass.itemGameAreaController.newBackground());
                        }
                    }
                }

            }
            else if (pressedKey == keySettingMap.get("rotate")) {
                if (setting.getGameMode() == 0) {
                    if (tempClass.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClass.itemGameAreaController.rotateBlock();
                        drawGameBoard(tempClass.itemGameAreaController.newBackground());
                    }
                } else {
                    if (tempClass.itemGameAreaController.ga.block.shape[0][0] == 9) {
                    } else {
                        if (tempClass.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClass.itemGameAreaController.rotateBlock();
                            drawGameBoard(tempClass.itemGameAreaController.newBackground());
                        }

                    }
                }
            }
            else if (pressedKey == keySettingMap.get("right")) {
                if (setting.getGameMode() == 0) {
                    if (tempClass.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClass.itemGameAreaController.moveBlockRight();
                        drawGameBoard(tempClass.itemGameAreaController.newBackground());
                    }
                } else {
                    if (tempClass.itemGameAreaController.ga.block.shape[0][0] == 9) {
                        if (tempClass.getChew()) {
                        } else {
                            tempClass.itemGameAreaController.moveBlockRight();
                            drawGameBoard(tempClass.itemGameAreaController.newBackground());
                        }
                    } else {
                        if (tempClass.itemGameAreaController.ga.block == null) {
                        } else {
                            tempClass.itemGameAreaController.moveBlockRight();
                            drawGameBoard(tempClass.itemGameAreaController.newBackground());
                        }
                    }
                }
            }
            else if (pressedKey == keySettingMap.get("down")) {
                if (setting.getGameMode() == 0) {
                    if (tempClass.itemGameAreaController.ga.block == null) {
                    } else {
                        tempClass.itemGameAreaController.moveBlockDown();
                        drawGameBoard(tempClass.itemGameAreaController.newBackground());
                    }
                }
                else {
                    if (tempClass.itemGameAreaController.ga.block == null || tempClass.getChew()) {
                    } else if (tempClass.itemGameAreaController.ga.block.shape[0][0] == 9){
                        tempClass.itemGameAreaController.moveBlockDown2();
                        drawGameBoard(tempClass.itemGameAreaController.newBackground());
                    }
                    else{
                        tempClass.itemGameAreaController.moveBlockDown();
                        drawGameBoard(tempClass.itemGameAreaController.newBackground());
                    }
                }
            }
            else if (pressedKey == keySettingMap.get("pause")) {
                if (!isStop) {
                    isStop = true;
                    timer.stop();
                }
            }
        }
    }

}