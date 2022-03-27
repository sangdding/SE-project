package view;

import controller.PageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeySettingPage extends JFrame {
    private JPanel mainPanel;
    private JPanel settingPanel;
    private JPanel buttonPanel;
    private JLabel upButton;
    private JLabel downButton;
    private JLabel rightButton;
    private JLabel leftButton;
    private JButton settingButton;
    private JLabel valueForUP;
    private JLabel valueFoDown;
    private JLabel valueFOrRIght;
    private JLabel valueForLeft;

    private PageController pageController;

    private JLabel[] labels = new JLabel[]{upButton, downButton, rightButton, leftButton};
    private JLabel[] values = new JLabel[]{valueForUP, valueFoDown, valueFOrRIght, valueForLeft};
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

        for (JLabel jl : labels) {
            jl.setOpaque(true);
        }
        
        //JSON 파일 읽어서 라벨 값 초기화 해야 함

        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x표 눌었을 때 프로세스 종료되게 만듦.
        this.setVisible(true);


        this.setLocationRelativeTo(null); //화면 가운데에 윈도우 생성


        //초기 버튼을 버튼 배열의 0번째 버튼으로 설정
        labels[0].setBackground(Color.RED);

        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);


    }

    private void setButtonClickController() {
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                pageController = new PageController(e.getActionCommand());
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
                            if (buttonSelectorIndex + 1 < labels.length) {
                                labels[buttonSelectorIndex].setBackground(null);
                                labels[++buttonSelectorIndex].setBackground(Color.RED);
                            }
                            break;
                        case KeyEvent.VK_UP: //방향키(위)눌렀을때
                            if (buttonSelectorIndex > 0) {
                                labels[buttonSelectorIndex].setBackground(null);
                                labels[--buttonSelectorIndex].setBackground(Color.RED);
                            }
                            break;
                        case KeyEvent.VK_ENTER:

                            isSetting = true;
                            labels[buttonSelectorIndex].setBackground(Color.GREEN);

                            break;
                        default:
                            break;
                    }
                }
                else{
                    if(e.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        isSetting = false;
                        labels[buttonSelectorIndex].setBackground(Color.RED);
                    }
                    else values[buttonSelectorIndex].setText(Integer.toString(e.getKeyCode()));
                }
            }
        });

    }
}
