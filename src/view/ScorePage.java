package view;


import controller.PageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ScorePage extends JFrame {

    private JPanel MainPanel;
    private JPanel ButtonPanel;
    private JButton mainButton;
    private JPanel ScorePanel;
    private JLabel titleLabel;


    private PageController pageController;

    public ScorePage() {

        initialize();
        setButtonClickController();


    }

    private void initialize() {
        
        //프레임 설정
        BorderLayout bl = new BorderLayout();
        this.setLayout(bl);

        //패널 생성
        ScorePanel=new JPanel();
        ButtonPanel= new JPanel();
        MainPanel= new JPanel();

        //컴포넌트 생성
        mainButton=new JButton("Main");
        titleLabel=new JLabel("Score");

        //패널 컴포넌트 연결
        ScorePanel.add(new scoreBoard());
        ButtonPanel.add(mainButton);

        //패널 add
        this.add(titleLabel,BorderLayout.NORTH);
        this.add(ScorePanel,BorderLayout.CENTER);
        this.add(ButtonPanel,BorderLayout.SOUTH);




        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x표 눌었을 때 프로세스 종료되게 만듦.

        this.setLocationRelativeTo(null);//화면 가운데에 생성



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


    public JPanel returnScorePanel() {
        this.setVisible(false);
        return this.ScorePanel;
    }




}
