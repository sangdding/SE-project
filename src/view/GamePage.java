package view;

import controller.PageController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.GameArea;
import model.GameThread;

public class GamePage extends JFrame{
    private JPanel gamePanel;
    private javax.swing.JPanel gameAreaPlaceholder;
    private GameArea ga;
    private JTextArea nextBlockTextArea;
    private JTextArea scoreBoardTextArea;
    private JButton mainButton;
    private JButton stopButton;
    private PageController pageController;

    public GamePage() {
        //초기화
        initialize();
        ga=new GameArea(gameAreaPlaceholder,10);
        this.add(ga);
        //키보드 이벤트 처리 설정
        setKeyEventController();
        //버튼 마우스 입력 처리 설정
        setButtonClickController();
        startGame();
    }

    public void startGame(){
        new GameThread(ga).start();
    }

    private void initialize(){

        this.add(gamePanel);

        this.setSize(500, 800); // 나중에 파일 입출력으로 세팅 파일에서 해상도 읽어오기
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x표 눌럿을 때 프로그램 종료되게 만듦



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
                        ga.dropBlock();
                        break;
                    case KeyEvent.VK_UP: //방향키(위)눌렀을때
                        ga.rotateBlock();
                        break;
                    case KeyEvent.VK_RIGHT:
                        ga.moveBlockRight();
                        break;
                    case KeyEvent.VK_LEFT:
                        ga.moveBlockLeft();
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
