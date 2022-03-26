package view;

import controller.PageController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameEndPage extends JFrame {
    private JPanel MainPanel;
    private JPanel InsetPanel;
    private JPanel ButtonPanel;
    private JButton enterButton;
    private JTextField NameTextField;
    private JButton mainButton;
    private JButton exitButton;
    private JButton gameRestartButton;
    private JLabel GameScore;
    private PageController pageController;

    public GameEndPage() {

        initialize();
        setButtonClickController();
    }

    private void initialize() {

        this.add(new ScorePage().returnScorePanel());
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


    }
}

