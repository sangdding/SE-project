package view;


import controller.PageController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;


public class ScorePage extends JFrame {

    private JPanel MainPanel;
    private JPanel ButtonPanel;
    private JLabel[] Scores = new JLabel[10];
    private JButton mainButton;
    private JPanel ScorePanel;

    private PageController pageController;

    public ScorePage() {

        initialize();
        setButtonClickController();


    }

    private void initialize()
    {
        this.add(MainPanel);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x표 눌었을 때 프로세스 종료되게 만듦.

        this.setLocationRelativeTo(null);//화면 가운데에 생성

        for (int i = 0; i < Scores.length; i++) {
            //파일에서 읽어오는 내용 추가

        }

        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);
    }

    private void setButtonClickController() {
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }
        });
    }


    public JPanel returnScorePanel()
    {
        this.setVisible(false);
        return this.ScorePanel;
    }
}
