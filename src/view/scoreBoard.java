package view;
import controller.HashMapParser;
import model.score.JsonScore;
import model.setting.JsonSetting;
import model.setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class scoreBoard extends JPanel{


    private static final int numScoreLine = 10;
    private static final int numLabelInTheLine = 4;
    private JLabel[] labels=new JLabel[numLabelInTheLine*numScoreLine];

    private Setting setting = new JsonSetting();

    scoreBoard(int gamemode)
    {
        GridLayout gl= new GridLayout(numScoreLine,numLabelInTheLine);
        this.setLayout(gl);

        HashMap<String, int[]> scoreInfo = new JsonScore().getList(gamemode);
        if(scoreInfo==null)
        {
            System.out.println("null");
            return;
        }

        HashMapParser hashmapparser = new HashMapParser();

        //hashmap은 정렬이 안 되어서 list로 변경하고 정렬

        java.util.List<Map.Entry<String,int[]>> orederedScoreInfo=hashmapparser.orederByDescent(scoreInfo);

        System.out.println(orederedScoreInfo + "  printed at ScorePage.Java");



        int checkedScoreNum=0;
        //배열 크기 맞추기
        for (int i = 0; i < numScoreLine && i<orederedScoreInfo.size(); i++) {

            if(orederedScoreInfo.get(i).getKey().equals("admin")) continue;

            //align 맞추기
            labels[numLabelInTheLine*i]=new JLabel(Integer.toString(i+1)+"위 : ");
            labels[numLabelInTheLine*i+1]=new JLabel(Integer.toString(orederedScoreInfo.get(i).getValue()[0]));
            labels[numLabelInTheLine*i+2]=new JLabel(orederedScoreInfo.get(i).getKey());
            labels[numLabelInTheLine*i+3]=new JLabel(Integer.toString(orederedScoreInfo.get(i).getValue()[1]));

            this.add(labels[numLabelInTheLine*i]);
            this.add(labels[numLabelInTheLine*i+1]);
            this.add(labels[numLabelInTheLine*i+2]);
            this.add(labels[numLabelInTheLine*i+3]);

            checkedScoreNum++;
        }

        for (int i = checkedScoreNum; i < numScoreLine ; i++) {


            //align 맞추기
            labels[numLabelInTheLine*i]=new JLabel(Integer.toString(i+1)+"위 : ");
            labels[numLabelInTheLine*i+1]=new JLabel("-");
            labels[numLabelInTheLine*i+2]=new JLabel("-");
            labels[numLabelInTheLine*i+3]=new JLabel("-");

            this.add(labels[numLabelInTheLine*i]);
            this.add(labels[numLabelInTheLine*i+1]);
            this.add(labels[numLabelInTheLine*i+2]);
            this.add(labels[numLabelInTheLine*i+3]);


            checkedScoreNum++;
        }

    }


}
