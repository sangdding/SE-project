/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.GamePage;

public class GameThread extends Thread{
    private GameArea ga;
    private GamePage gp;
    private Boolean C=false;
    private Boolean item=false;
    private int i=0;
    private int score=0;
    private int wait=1000;
    private int[][][]shapes={
            {{1},{1},{1},{1}},{{1,0,0},{1,1,1}},{{0,0,1},{1,1,1}},{{1,1},{1,1}},
            {{0,1,1},{1,1,0}},{{0,1,0},{1,1,1}},{{1,1,0},{0,3,3}}
    };
    private Color[] colors={Color.RED,Color.RED,Color.RED,Color.RED,Color.RED,Color.RED,Color.RED};

    public GameThread(GameArea ga,GamePage gp){ //gameArea를 넘김
    this.ga=ga;
    this.gp=gp;
    }

    @Override
    public void run(){//런이 꺼지면 쓰레드가 날아가기때문에 무한루푸 실행해줘야함.

        while(true){
            ga.spawnBlock(shapes[0],); //블럭 추가
            while(ga.checkBottom()){
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(ga.isBlockOuOofBounds()){
                System.out.println("GameOver");
                break;
            }
            if(i == 3 ){i=0;} else{i++;}
            ga.moveBlockToBackground();
            if(ga.clearLines() > 1){score=score+ga.clearLines()*2;}
            else {score+=ga.clearLines();}
            gp.setScore(score);
            if(ga.clearLines() != 0){wait -=50;}}}
        }
    }



