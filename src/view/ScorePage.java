package view;


import controller.HashMapParser;
import controller.PageController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.JsonScore;


import java.util.*;


public class ScorePage extends JFrame {

    private JPanel MainPanel;
    private JPanel ButtonPanel;
    private JButton mainButton;
    private JPanel ScorePanel;
    private JLabel score1;
    private JLabel score2;
    private JLabel score3;
    private JLabel score4;
    private JLabel score5;
    private JLabel score6;
    private JLabel score7;
    private JLabel score8;
    private JLabel score9;
    private JLabel score10;

    private JLabel[] scoreLables = new JLabel[]{
        score1, score2, score3, score4, score5, score6, score7, score8, score9, score10};
    private PageController pageController;

    public ScorePage() {

        initialize();
        setButtonClickController();


    }

    private void initialize() {
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


    public JPanel returnScorePanel() {
        this.setVisible(false);
        return this.ScorePanel;
    }

    private void setScoreBoard() {
        HashMap<String, Integer> scoreInfo = new JsonScore().getList();
        HashMapParser hashmapparser = new HashMapParser();

        //hashmap은 정렬이 안 되어서 list로 변경
        java.util.List<Map.Entry<String,Integer>> orederedScoreInfo=hashmapparser.orederByDescent(scoreInfo);







        System.out.println(orederedScoreInfo + "  printed at ScorePage.Java");
        for (int i = 0; i < 10; i++) {

            scoreLables[i].setText(Integer.toString(orederedScoreInfo.get(i).getValue()) + " by "+orederedScoreInfo.get(i).getKey());

        }

    }


}
