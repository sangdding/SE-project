package view;

import controller.PageController;
import model.JsonScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameEndPage extends JFrame {

    private JPanel InsertPanel;
    private JPanel ButtonPanel;
    private JButton nameEnterButton;
    private JTextField nameTextField;
    private JButton mainButton;
    private JButton exitButton;
    private JButton gameRestartButton;
    private JLabel gameScoreLabel;
    private JLabel nameLabel;
    private JPanel scoreBoardPanel;
    private PageController pageController;

    private boolean scoreIsEntered =false;

    public GameEndPage() {

        initialize();
        setButtonClickController();
    }

    private void initialize() {

        //프레임 설정
        BorderLayout bl = new BorderLayout();
        this.setLayout(bl);
        
        //패널 생성
        scoreBoardPanel=new JPanel();
        InsertPanel = new JPanel();
        ButtonPanel = new JPanel();

        GridLayout gl = new GridLayout(3,1);
        ButtonPanel.setLayout(gl);

        //컴포넌트 생성
        nameEnterButton=new JButton("Enter");
        nameTextField = new JTextField();
        gameScoreLabel = new JLabel("1111");
        nameLabel=new JLabel("Name");

        mainButton=new JButton("Main");
        gameRestartButton = new JButton("Game Restart");
        exitButton = new JButton("Exit");


        //패널 컴포넌트 연결
        scoreBoardPanel=new scoreBoard();

        InsertPanel.add(nameLabel);
        InsertPanel.add(nameTextField);
        InsertPanel.add(gameScoreLabel);
        InsertPanel.add(nameEnterButton);

        ButtonPanel.add(mainButton);
        ButtonPanel.add(gameRestartButton);
        ButtonPanel.add(exitButton);


        //패널 add
        this.add(scoreBoardPanel,BorderLayout.CENTER);
        this.add(InsertPanel,BorderLayout.SOUTH);
        this.add(ButtonPanel,BorderLayout.EAST);

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

                    updateScore();


                    for(int i=0;i<10;i++)
                    {
                        Component c = scoreBoardPanel.getComponent(3*i+2);
                        if(c instanceof JLabel)
                        {
                            JLabel jl=(JLabel) c;
                            if(jl.getText().equals(nameTextField.getText()))
                            {
                                System.out.println(jl.getText());
                                jl.setOpaque(true);
                                jl.setForeground(Color.RED);
                            }
                        }
                    }



                }
                else System.out.println("Name is empty");
            }
        });

    }

    private void updateScore()
    {
        this.remove(scoreBoardPanel);
        this.scoreBoardPanel=new scoreBoard();
        this.add(scoreBoardPanel,BorderLayout.CENTER);
        this.validate();
    }

}

