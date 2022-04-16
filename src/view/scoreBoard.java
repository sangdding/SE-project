package view;
import controller.HashMapParser;
import model.score.JsonScore;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class scoreBoard extends JPanel{


    private static final int numScoreLine = 10;
    private static final int numLabelInTheLine = 3;
    private JLabel[] labels=new JLabel[numLabelInTheLine*numScoreLine];
    scoreBoard()
    {
        GridLayout gl= new GridLayout(numScoreLine,numLabelInTheLine);
        this.setLayout(gl);

        HashMap<String, Integer> scoreInfo = new JsonScore().getList();
        HashMapParser hashmapparser = new HashMapParser();

        //hashmap은 정렬이 안 되어서 list로 변경
        java.util.List<Map.Entry<String,Integer>> orederedScoreInfo=hashmapparser.orederByDescent(scoreInfo);

        System.out.println(orederedScoreInfo + "  printed at ScorePage.Java");



        int checkedScoreNum=0;
        //배열 크기 맞추기
        for (int i = 0; i < numScoreLine && i<orederedScoreInfo.size(); i++) {

            if(orederedScoreInfo.get(i).getKey().equals("admin")) continue;

            //align 맞추기
            labels[3*i]=new JLabel(Integer.toString(i+1)+"위 : ");
            labels[3*i+1]=new JLabel(Integer.toString(orederedScoreInfo.get(i).getValue()));
            labels[3*i+2]=new JLabel(orederedScoreInfo.get(i).getKey());
            this.add(labels[3*i]);
            this.add(labels[3*i+1]);
            this.add(labels[3*i+2]);

            checkedScoreNum++;
        }

        for (int i = checkedScoreNum; i < numScoreLine ; i++) {


            //align 맞추기
            labels[3*i]=new JLabel(Integer.toString(i+1)+"위 : ");
            labels[3*i+1]=new JLabel("-");
            labels[3*i+2]=new JLabel("-");
            this.add(labels[3*i]);
            this.add(labels[3*i+1]);
            this.add(labels[3*i+2]);

            checkedScoreNum++;
        }

    }


}
