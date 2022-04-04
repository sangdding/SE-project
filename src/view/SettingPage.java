package view;

import config.AppConfig;
import controller.PageController;
import model.score.JsonScore;
import model.score.Score;
import model.setting.JsonSetting;
import model.setting.Setting;

import javax.swing.*;
import java.awt.event.*;

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

    AppConfig appConfig = new AppConfig();
    Setting setting = appConfig.setting();


    public SettingPage() {


        initialize();


        setButtonClickController();




    }
    private void initialize()
    {


        this.add(settingPanel);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x표 눌었을 때 프로세스 종료되게 만듦.

        this.setLocationRelativeTo(null);//화면 가운데에 생성

        //세팅 파일에서 읽어와서 display mode 1이면 색맹모드이니 체크박스 체크.

        if(setting.getDisplayMode()==1) colorBlindnessCheckBox.setSelected(true);
        else colorBlindnessCheckBox.setSelected(false);


        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);
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

                setting.setDisplaySize(500,800);

            }
        });
        resolutionButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setting.setDisplaySize(800,800);
            }
        });
        resolutionButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setting.setDisplaySize(1000,1500);
            }
        });

        colorBlindnessCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange()==ItemEvent.SELECTED)
                {

                    System.out.println("bilnd mode selected");
                    setting.setDisplayMode(1);
                }
                else if(e.getStateChange()==ItemEvent.DESELECTED){

                    System.out.println("blind mode disselected");
                    setting.setDisplayMode(0);
                }
            }
        });

        settingResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Score score = new JsonScore();

                //점수 초기화
                score.resetList();
                //키세팅 초기화
                setting.setDefaultKeySet();
                //화면 크기 초기화
                setting.setDisplaySize(500,800);
                //난이도,게임모드, 색맹모드 초기화
                setting.setDifficulty(1);
                setting.setGameMode(1);
                setting.setDisplayMode(0);

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


}
