package view;

import controller.PageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import model.setting.JsonSetting;


public class KeySettingPage extends JFrame {
    private JPanel mainPanel;
    private JPanel settingPanel;
    private JPanel buttonPanel;
    private JLabel upButton;
    private JLabel downButton;
    private JLabel rightButton;
    private JLabel leftButton;
    private JButton settingButton;
    private JLabel valueForUp;
    private JLabel valueForDown;
    private JLabel valueForRight;
    private JLabel valueForLeft;
    private JButton resetButton;
    private JLabel terminateButton;
    private JLabel pauseButton;
    private JLabel resumeButton;
    private JLabel quickDownButton;
    private JLabel dropButton;
    private JLabel valueForTerminate;
    private JLabel valueForPause;
    private JLabel valueForResume;
    private JLabel valueForQuickDown;
    private JLabel valueForDrop;

    private PageController pageController;

    private JLabel[] keyLabels = new JLabel[]{upButton, downButton, rightButton, leftButton, terminateButton,
                                                pauseButton, resumeButton, quickDownButton,dropButton};
    private JLabel[] keyValues = new JLabel[]{valueForUp, valueForDown, valueForRight, valueForLeft, valueForTerminate,
                                                valueForPause,valueForResume, valueForQuickDown, valueForDrop};
    private int buttonSelectorIndex = 0;
    private boolean isSetting = false;

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
        
        //키 세팅 밸류 라벨 값 초기화 해야 함
        displaySettingValues();

        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x표 눌었을 때 프로세스 종료되게 만듦.
        this.setVisible(true);


        this.setLocationRelativeTo(null); //화면 가운데에 윈도우 생성


        //초기 버튼을 버튼 배열의 0번째 버튼으로 설정
        keyLabels[0].setBackground(Color.RED);

        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);


    }

    private void setButtonClickController() {
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //json 파일에 세팅 저장
                JsonSetting jsonsettign = new JsonSetting();
                HashMap<String,Integer> keyMap=new HashMap<>();

                for(int i=0;i<keyLabels.length;i++)
                {
                    keyMap.put(keyLabels[i].getText(),
                            Integer.parseInt(keyValues[i].getText()));
                }
                jsonsettign.setKeyList(keyMap);


                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JsonSetting jsonsetting=new JsonSetting();
                jsonsetting.setDefaultKeySet();
                //화면 초기화
                displaySettingValues();

                //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
                requestFocus();
                setFocusable(true);
            }
        });

    }

    private void setKeyEventController() {
        addKeyListener(new KeyAdapter() { //키 이벤트
            @Override
            public void keyPressed(KeyEvent e) { //키 눌렀을때
                System.out.println(e.getKeyCode());
                // TODO Auto-generated method stub
                if (isSetting == false) {
                    switch (e.getKeyCode()) {//키 코드로 스위치


                        case KeyEvent.VK_DOWN: //방향키(아래) 눌렀을때
                            if (buttonSelectorIndex + 1 < keyLabels.length) {
                                keyLabels[buttonSelectorIndex].setBackground(null);
                                keyLabels[++buttonSelectorIndex].setBackground(Color.RED);
                            }
                            break;
                        case KeyEvent.VK_UP: //방향키(위)눌렀을때
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
                    if(e.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        for(int i=0;i<keyValues.length;i++)
                        {
                            if(i==buttonSelectorIndex) continue;

                            if (keyValues[i].getText().equals(keyValues[buttonSelectorIndex].getText()))
                            {
                                System.out.println("key Duplicated");
                                return;
                            }
                        }

                        //여기 수정
                        isSetting = false;
                        keyLabels[buttonSelectorIndex].setBackground(Color.RED);
                    }
                    else keyValues[buttonSelectorIndex].setText(Integer.toString(e.getKeyCode()));
                }
            }
        });

    }
    private void displaySettingValues()
    {
        JsonSetting jsonsetting=new JsonSetting();
        HashMap<String,Integer> keyMap=jsonsetting.getKeyList();

        for(int i=0;i<keyValues.length;i++)
            keyValues[i].setText(Integer.toString(keyMap.get(keyLabels[i].getText())));


    }
}
