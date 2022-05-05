package view;

import controller.PageController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VersusGameEndPage extends JFrame {
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JButton mainButton;
    private JPanel labelPanel;
    private JLabel winnerLabel;

    private PageController pageController;


    VersusGameEndPage(int winner)
    {


        this.setSize(500, 500);



        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x표 눌었을 때 프로세스 종료되게 만듦.
        this.setVisible(true);


        this.setLocationRelativeTo(null); //화면 가운데에 윈도우 생성




        //포커스를 이 화면에 맟춰서 키 이벤트 받게 만듦
        requestFocus();
        setFocusable(true);





        winnerLabel.setText(Integer.toString(winner)+"P is winner");
        setButtonClickController();

    }

    private void setButtonClickController() {
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                pageController = new PageController(e.getActionCommand());
            }
        });
    }
}
