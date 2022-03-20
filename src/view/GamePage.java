package view;

import controller.PageController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePage extends JFrame{
    private JPanel gamePanel;
    private JTextArea gameBoardTextArea;
    private JTextArea nextBlockTextArea;
    private JTextArea scoreBoardTextArea;
    private JButton mainButton;
    private JButton stopButton;
    private PageController pageController;

    public GamePage() {


        //초기화
        initialize();
        //키보드 이벤트 처리 설정
        setKeyEventController();
        //버튼 마우스 입력 처리 설정
        setButtonClickController();

    }


    private void initialize(){

        this.add(gamePanel);
        this.setSize(500, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x표 눌럿을 때 프로그램 종료되게 만듦



        gameBoardTextArea.setRows(20); gameBoardTextArea.setColumns(10); // 게임 칸은 20*10
        gameBoardTextArea.setBorder(new LineBorder(Color.RED));




        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);



        //화면 가운데에 생성
        this.setLocationRelativeTo(null);

    }
    private void setKeyEventController()
    {
        addKeyListener(new KeyAdapter() { //키 이벤트
            @Override
            public void keyPressed(KeyEvent e) { //키 눌렀을때
                System.out.println("game page key event enter");
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

    private void setButtonClickController()
    {
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageController = new PageController(e.getActionCommand());
                JOptionPane.showMessageDialog(null, "stopped!!");
            }
        });

    }


}
