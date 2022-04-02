package view;

import controller.PageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class MainPage extends JFrame{

    private JPanel mainPanel;

    private JButton gameStartButton;
    private JButton settingButton;
    private JButton scoreBoardButton;
    private JButton exitButton;


    private PageController pageController;

    private JButton[] buttons=new JButton[]{gameStartButton,settingButton,scoreBoardButton, exitButton };
    private int buttonSelectorIndex =0;



    //생성자
    //화면이 전환될 때마다 생성자 호출
    public MainPage() {

        //초기화
        initialize();
        //키보드 입력 이벤트
        setKeyEventController();
        //버튼 클릭 이벤트 설정
        setButtonClickController();


    }


    private void initialize()
    {

        this.setTitle("Main");

        this.setSize(500, 500);

        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x표 눌었을 때 프로세스 종료되게 만듦.
        this.setVisible(true);


        this.setLocationRelativeTo(null); //화면 가운데에 윈도우 생성


        //초기 버튼을 버튼 배열의 0번째 버튼으로 설정
        buttons[0].setBackground(Color.RED);

        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);


    }
    private void setKeyEventController()
    {
        addKeyListener(new KeyAdapter() { //키 이벤트
            @Override
            public void keyPressed(KeyEvent e) { //키 눌렀을때
                System.out.println("main page key event enter");
                System.out.println(e.getKeyCode());
                // TODO Auto-generated method stub
                switch (e.getKeyCode()) {//키 코드로 스위치

                    case KeyEvent.VK_DOWN: //방향키(아래) 눌렀을때
                        if (buttonSelectorIndex +1<buttons.length)
                        {
                            buttons[buttonSelectorIndex].setBackground(null);
                            buttons[++buttonSelectorIndex].setBackground(Color.RED);
                        }
                        break;
                    case KeyEvent.VK_UP: //방향키(위)눌렀을때
                        if(buttonSelectorIndex >0)
                        {
                            buttons[buttonSelectorIndex].setBackground(null);
                            buttons[--buttonSelectorIndex].setBackground(Color.RED);
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        setVisible(false);
                        pageController = new PageController(buttons[buttonSelectorIndex].getText());
                        break;

                    default:
                        break;
                }

            }
        });

    }
    private void setButtonClickController(){
        gameStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }
        });



        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }

        });

        scoreBoardButton.addActionListener(new ActionListener() {
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
