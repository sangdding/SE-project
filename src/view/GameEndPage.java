package view;

import controller.PageController;
import model.score.JsonScore;

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
        nameTextField = new JTextField("",10);
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


                if(!nameTextField.getText().isEmpty())
                {
                    JsonScore score=new JsonScore();

                    int retSave=score.save(nameTextField.getText(),3000);
                    if(retSave==1){
                        System.out.println("Duplicated Name");
                        return;
                    }
                    else if(retSave==-1)
                    {
                        System.out.println("Input Output  Error");
                        return;
                    };

                    scoreIsEntered=true;
                    updateScoreBoard();

                    //하이라이트
                    System.out.println(scoreBoardPanel.getComponents().length);
                    for(int i=0;i<10 && 3*i<scoreBoardPanel.getComponents().length;i++)
                    {
                        Component c1 = scoreBoardPanel.getComponent(3*i);
                        Component c2 = scoreBoardPanel.getComponent(3*i+1);
                        Component c3 = scoreBoardPanel.getComponent(3*i+2);
                        if(c3 instanceof JLabel)
                        {
                            JLabel jl3=(JLabel) c3;
                            if(jl3.getText().equals(nameTextField.getText()))
                            {
                                JLabel jl1=(JLabel) c1;
                                JLabel jl2=(JLabel) c2;

                                jl1.setOpaque(true);
                                jl1.setForeground(Color.RED);

                                jl2.setOpaque(true);
                                jl2.setForeground(Color.RED);

                                jl3.setOpaque(true);
                                jl3.setForeground(Color.RED);
                            }
                        }
                    }



                }
                else System.out.println("Name is empty");
            }
        });

    }

    private void updateScoreBoard()
    {
        this.remove(scoreBoardPanel);
        this.scoreBoardPanel=new scoreBoard();
        this.add(scoreBoardPanel,BorderLayout.CENTER);
        this.validate();
    }

}

