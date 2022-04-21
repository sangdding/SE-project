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

    private JsonScore jsonScore=new JsonScore();

    scoreBoard(int gamemode)
    {
        GridLayout gl= new GridLayout(numScoreLine,numLabelInTheLine);
        this.setLayout(gl);

        HashMap<String,Integer>scoreInfo = jsonScore.getScorelist(gamemode);
        HashMap<String,Integer>difficultyInfo = jsonScore.getDifficultyList(gamemode);


        HashMapParser hashmapparser = new HashMapParser();

        //hashmap은 정렬이 안 되어서 list로 변경하고 정렬

        java.util.List<Map.Entry<String,Integer>> orederedScoreInfo=hashmapparser.orederByDescent(scoreInfo);

        System.out.println(orederedScoreInfo + "  printed at ScorePage.Java");




        int checkedScoreNum=0;
        //배열 크기 맞추기
        for (int i = 0; i < numScoreLine && i<orederedScoreInfo.size(); i++) {

            if(orederedScoreInfo.get(i).getKey().equals("admin")) continue;

            //align 맞추기
            labels[numLabelInTheLine*i]=new JLabel(Integer.toString(i+1)+"위 : ");
            labels[numLabelInTheLine*i+1]=new JLabel(Integer.toString(orederedScoreInfo.get(i).getValue())); // 점수
            labels[numLabelInTheLine*i+2]=new JLabel(orederedScoreInfo.get(i).getKey());//이름

            int diff=difficultyInfo.get(orederedScoreInfo.get(i).getKey()); //난이도

            if (diff==0) labels[numLabelInTheLine*i+3]=new JLabel("Normal"); // easy
            else if (diff==1) labels[numLabelInTheLine*i+3]=new JLabel("Easy"); // easy
            else if (diff==2) labels[numLabelInTheLine*i+3]=new JLabel("Hard"); // easy

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
