package view;

import controller.PageController;
import model.score.JsonScore;
import model.score.Score;
import model.setting.JsonSetting;
import model.setting.Setting;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SettingPage extends JFrame{
    private JPanel settingPanel;
    private JPanel buttonPanel;
    private JPanel resolutionPanel;
    private PageController pageController;

    private JButton mainButton;
    private JButton resolutionButton1;
    private JButton resolutionButton2;
    private JButton resolutionButton3;
    private JButton settingResetButton;
    private JButton scoreboardResetButton;
    private JCheckBox colorBlindnessCheckBox;
    private JButton keySettingButton;




    public SettingPage() {


        initialize();
        //키보드 이벤트 처리 설정
        setKeyEventController();

        setButtonClickController();




    }
    private void initialize()
    {
        this.add(settingPanel);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x표 눌었을 때 프로세스 종료되게 만듦.

        this.setLocationRelativeTo(null);//화면 가운데에 생성

        //세팅 파일에서 읽어와서 색맹모드 1이면 체크박스 체크. if 문 안에 세팅 파일 속 색맹모드 여부 체크
        if(1==0) colorBlindnessCheckBox.setSelected(true);

        else colorBlindnessCheckBox.setSelected(false);


        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);
    }



    private void setKeyEventController()
    {
        addKeyListener(new KeyAdapter() { //키 이벤트
            @Override
            public void keyPressed(KeyEvent e) { //키 눌렀을때
                System.out.println("setting page key event enter");
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

    private void setButtonClickController(){
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }
        });

        resolutionButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setDisplaysize(500,800);

            }
        });
        resolutionButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDisplaysize(800,800);
            }
        });
        resolutionButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDisplaysize(1000,1500);
            }
        });
        settingResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //파일 입출력을 통해 세팅 txt 초기화
            }
        });
        scoreboardResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Score score= new JsonScore();
                score.resetList();
            }
        });

        keySettingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //키세팅 페이지로 전환
                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }
        });

    }

    private void setDisplaysize(int w, int h)
    {
        Setting setting = new JsonSetting();
        setting.setDisplaySize(w,h);
    }
}
