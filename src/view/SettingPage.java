package view;

import controller.PageController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SettingPage extends JFrame{
    private JSlider slider1;
    private JPanel settingPanel;
    private JPanel settingSubPanel;
    private JLabel left;
    private JLabel right;
    private JButton mainButton;
    private JPanel bottomPanel;
    private PageController pageController;

    public SettingPage() {

        this.add(settingPanel);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x표 눌었을 때 프로세스 종료되게 만듦.

        this.setLocationRelativeTo(null);//화면 가운데에 생성


        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);

        //키보드 이벤트 처리 설정
        setKeyEventController();


        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }
        });
    }

    private void setKeyEventController()
    {
        addKeyListener(new KeyAdapter() { //키 이벤트
            @Override
            public void keyPressed(KeyEvent e) { //키 눌렀을때
                System.out.println("setting page key event enter");
                System.out.println(e.getKeyCode());
                // TODO Auto-generated method stub
                switch (e.getKeyCode()) {//키 코드로 스위치

                    case KeyEvent.VK_DOWN: //방향키(아래) 눌렀을때

                        break;
                    case KeyEvent.VK_UP: //방향키(위)눌렀을때

                        break;

                    default:
                        break;
                }
            }
        });

    }
}
