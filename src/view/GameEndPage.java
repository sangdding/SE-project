package view;

import controller.PageController;
import model.JsonScore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameEndPage extends JFrame {
    private JPanel MainPanel;
    private JPanel InsetPanel;
    private JPanel ButtonPanel;
    private JButton nameEnterButton;
    private JTextField nameTextField;
    private JButton mainButton;
    private JButton exitButton;
    private JButton gameRestartButton;
    private JLabel gameScore;
    private JPanel scoreBoardPanel;
    private PageController pageController;

    private boolean scoreIsEntered =false;

    public GameEndPage() {

        initialize();
        setButtonClickController();
    }

    private void initialize() {

        scoreBoardPanel=new ScorePage().returnScorePanel();
        this.add(scoreBoardPanel);
        this.add(MainPanel);

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

        gameRestartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        nameEnterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(scoreIsEntered==true)
                {
                    System.out.println("Already entered score");
                    return;
                }

                scoreIsEntered=true;
                if(!nameTextField.getText().isEmpty())
                {
                    JsonScore score=new JsonScore();
                    score.save(nameTextField.getText(),1111);

                    

                }
                else System.out.println("Name is empty");
            }
        });

    }
}

