package view;

import controller.PageController;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePage extends JFrame{
    private JPanel mainPanel;
    private javax.swing.JPanel gameBoardPanel;

    private JButton mainButton;
    private JButton stopButton;
    private JPanel buttonPanel;
    private JPanel nextBlockPanel;
    private JTextPane gameBoardPane;
    private JTextPane nextBlockPane;
    private JPanel scorePanel;
    private JButton exitButton;
    private PageController pageController;
    private int score;
    private boolean isStop;
    private Timer timer;

    private int width=10;
    private int height=20;
    private char borderChar='X';
    private SimpleAttributeSet styleSet;

    public GamePage() {
        //초기화
        initialize();

        //키보드 이벤트 처리 설정
        setKeyEventController();
        //버튼 마우스 입력 처리 설정
        setButtonClickController();

        //timer 설정
        setTimer();
    }

    private void initialize(){

        //스타일
        styleSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet, 18);
        StyleConstants.setFontFamily(styleSet, "comic sans");
        StyleConstants.setBold(styleSet, false);
        StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);

        drawGameBoard();

        gameBoardPane.setEditable(false);
        this.add(mainPanel);
        this.setSize(500, 800); // 나중에 파일 입출력으로 세팅 파일에서 해상도 읽어오기
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x표 눌럿을 때 프로그램 종료되게 만듦


        isStop=false;

        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);



        //화면 가운데에 생성
        this.setLocationRelativeTo(null);

    }

    public void setScore(int score){

        this.score=score;
    }


    private void setKeyEventController()
    {
        addKeyListener(new KeyAdapter() { //키 이벤트
            @Override
            public void keyPressed(KeyEvent e) { //키 눌렀을때
                System.out.println("game page key event enter in GamePage");
                System.out.println(e.getKeyCode());
                // TODO Auto-generated method stub
                switch (e.getKeyCode()) {//키 코드로 스위치

                    case KeyEvent.VK_DOWN: //방향키(아래) 눌렀을때

                        break;
                    case KeyEvent.VK_UP: //방향키(위)눌렀을때

                        break;
                    case KeyEvent.VK_RIGHT:

                        break;
                    case KeyEvent.VK_LEFT:

                        break;

                    case KeyEvent.VK_S:
                        //game stop
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
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    private void setTimer()
    {
        Timer timer = new Timer(1000, new ActionListener()

        {

            public void actionPerformed (ActionEvent e)

            {

                System.out.println("timer activated in Game page");
                //System.out.println("draw gameBoard in Game page");
                //drawGameBoard();

            }

        });
        timer.start();
    }

    private void drawGameBoard()
    {
        gameBoardPane.setMargin(new Insets(130,0,0,0));
        //여기서부턴 화면에 그리기

        appendToPane(gameBoardPane,"XXXXXXXXXXXX\n",Color.BLACK);

        for(int i=0;i<20;i++)
        {
            appendToPane(gameBoardPane,"X",Color.BLACK);
            appendToPane(gameBoardPane,"AAAAAAAAAA",Color.WHITE);
            appendToPane(gameBoardPane,"X\n",Color.BLACK);
        }



        appendToPane(gameBoardPane,"XXXXXXXXXXXX",Color.BLACK);


        StyledDocument doc = gameBoardPane.getStyledDocument();


        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        gameBoardPane.setStyledDocument(doc);
        
        /* 이건 블럭 별로 색깔 넣기
        StyledDocument doc = pane.getStyledDocument();
		SimpleAttributeSet styles = new SimpleAttributeSet();
		StyleConstants.setForeground(styles, curr.getColor());
        * */
    }

    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}
