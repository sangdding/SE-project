package view;


import controller.PageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.JsonScore;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;


public class ScorePage extends JFrame {

    private JPanel MainPanel;
    private JPanel ButtonPanel;
    //private JLabel[] Scores = new JLabel[10];
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



        setScoreBoard();

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

    private void setScoreBoard()
    {
        HashMap<String,Integer> scoreInfo= new JsonScore().getList();
        ArrayList<Integer> scores = new ArrayList<Integer>();

        Iterator<String> iter = scoreInfo.keySet().iterator();
        while(iter.hasNext()) {
            String key = iter.next();
            scores.add(scoreInfo.get(key));
        }
        System.out.println(scoreInfo);

        Iterator<Integer> iter2=scores.iterator();
        for(int i=0;i<10;i++)
        {
            Component c = ScorePanel.getComponent(2*i+1);
            if(c instanceof JLabel)
            {
                JLabel jl=(JLabel) c;
                jl.setText(Integer.toString(iter2.next()));
            }
        }

    }
}
