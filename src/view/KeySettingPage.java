package view;

import controller.PageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import model.setting.JsonSetting;

import static java.awt.event.KeyEvent.getKeyText;


public class KeySettingPage extends JFrame {
    private JPanel mainPanel;
    private JPanel settingPanel;
    private JPanel buttonPanel;
    private JLabel rotateButton;
    private JLabel downButton;
    private JLabel rightButton;
    private JLabel leftButton;
    private JButton settingButton;
    private JLabel valueForRotate;
    private JLabel valueForDown;
    private JLabel valueForRight;
    private JLabel valueForLeft;
    private JButton resetButton;
    private JLabel terminateButton;
    private JLabel pauseButton;
    private JLabel resumeButton;
    private JLabel dropButton;
    private JLabel valueForTerminate;
    private JLabel valueForPause;
    private JLabel valueForResume;

    private JLabel valueForDrop;
    private JLabel selectPlayerLabel;

    private PageController pageController;

    private JLabel[] keyLabels = new JLabel[]{rotateButton, downButton, rightButton, leftButton, dropButton, terminateButton,
                                                pauseButton, resumeButton};
    private JLabel[] keyValueLabels = new JLabel[]{valueForRotate, valueForDown, valueForRight, valueForLeft,  valueForDrop, valueForTerminate,
                                                valueForPause,valueForResume};
    private int[] keyValues=new int[8];

    private int buttonSelectorIndex = 0;
    private boolean isSetting = false;
    private JsonSetting jsonsetting=new JsonSetting("player1");

    private int selectedPlayer=1;

    private KeySettingPageKeyListener keySettingPageKeyListener;

    public KeySettingPage() {
        initialize();
        setButtonClickController();
        setKeyEventController();

    }

    private void initialize() {

        this.setTitle("Key Setting");

        this.setSize(500, 500);

        for (JLabel jl : keyLabels) {
            jl.setOpaque(true);
        }
        
        //??? ?????? ?????? ?????? ??? ????????? ?????? ???
        displaySettingValues();

        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x??? ????????? ??? ???????????? ???????????? ??????.
        this.setVisible(true);


        this.setLocationRelativeTo(null); //?????? ???????????? ????????? ??????


        //?????? ????????? ?????? ????????? 0?????? ???????????? ??????
        keyLabels[0].setBackground(Color.RED);

        //???????????? ??? ????????? ????????? ??? ????????? ?????? ??????
        requestFocus();
        setFocusable(true);


    }

    private void setButtonClickController() {
        settingButton.addActionListener(new ActionListener() {
            //??? ?????? ???????????? ?????? ???????????? ?????? ???
            @Override
            public void actionPerformed(ActionEvent e) {

                HashMap<String,Integer> keyMap=new HashMap<>();

                for(int i=0;i<keyLabels.length;i++)
                {
                    keyMap.put(keyLabels[i].getText(),
                            keyValues[i]);
                }
                jsonsetting.setKeyList(keyMap);


                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jsonsetting.setDefaultKeySet();
                //?????? ?????????
                displaySettingValues();

                //???????????? ??? ????????? ????????? ??? ????????? ?????? ??????
                requestFocus();
                setFocusable(true);
            }
        });

    }

    private void setKeyEventController() {
        keySettingPageKeyListener=new KeySettingPageKeyListener();
        addKeyListener(keySettingPageKeyListener);

    }
    private void displaySettingValues()
    {

        HashMap<String,Integer> keyMap=jsonsetting.getKeyList();

        for(int i = 0; i< keyValueLabels.length; i++) {

            keyValues[i]=keyMap.get(keyLabels[i].getText());
            keyValueLabels[i].setText(getKeyText(keyValues[i]));
        }
    }

    public class KeySettingPageKeyListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {


        }
        @Override
        public void keyPressed(KeyEvent e) { //??? ????????????
            System.out.println(e.getKeyCode());
            // TODO Auto-generated method stub
            if (isSetting == false) {
                switch (e.getKeyCode()) {//??? ????????? ?????????
                    case KeyEvent.VK_C:{ //c ????????? ?????????
                        if(selectedPlayer==1) {
                            jsonsetting = new JsonSetting("player2");
                            selectedPlayer=2;
                            selectPlayerLabel.setText("2P (Press C to Change 1P)");
                            displaySettingValues();
                        }

                        else if(selectedPlayer==2)
                        {
                            jsonsetting = new JsonSetting("player1");
                            selectedPlayer=1;
                            selectPlayerLabel.setText("1P (Press C to Change 2P)");
                            displaySettingValues();
                        }
                        break;
                    }

                    case KeyEvent.VK_DOWN: //?????????(??????) ????????????
                        if (buttonSelectorIndex + 1 < keyLabels.length) {
                            keyLabels[buttonSelectorIndex].setBackground(null);
                            keyLabels[++buttonSelectorIndex].setBackground(Color.RED);
                        }
                        break;
                    case KeyEvent.VK_UP: //?????????(???)????????????
                        if (buttonSelectorIndex > 0) {
                            keyLabels[buttonSelectorIndex].setBackground(null);
                            keyLabels[--buttonSelectorIndex].setBackground(Color.RED);
                        }
                        break;
                    case KeyEvent.VK_ENTER:

                        isSetting = true;
                        keyLabels[buttonSelectorIndex].setBackground(Color.GREEN);

                        break;
                    default:
                        break;
                }
            }
            else{
                //??? ?????? ??????
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                {
                    for(int i = 0; i< keyValueLabels.length; i++)
                    {
                        if(i==buttonSelectorIndex) continue;

                        if (keyValueLabels[i].getText().equals(keyValueLabels[buttonSelectorIndex].getText()))
                        {
                            System.out.println("key Duplicated");
                            return;
                        }
                    }

                    //?????? ??????
                    isSetting = false;
                    keyLabels[buttonSelectorIndex].setBackground(Color.RED);
                }
                else {
                    keyValues[buttonSelectorIndex] = e.getKeyCode();
                    keyValueLabels[buttonSelectorIndex].setText(getKeyText(keyValues[buttonSelectorIndex]));
                }
            }
        }
    }

}
