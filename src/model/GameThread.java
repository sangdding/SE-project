/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameThread extends Thread{
    private GameArea ga;
    private int wait=1000;
    private int[][][] arr={{{1,0},{1,0},{1,1}},{{1,0},{1,0},{1,0}},{{0,1},{1,1},{1,0}},{{1,0},{1,1},{1,0}}};
    private int i=0;

    public GameThread(GameArea ga){ //gameArea를 넘김
        this.ga= ga;
    }
    @Override
    public void run(){//런이 꺼지면 쓰레드가 날아가기때문에 무한루푸 실행해줘야함.
        while(true){
            ga.spawnBlock(arr[i]); //블럭 추가
            while(ga.moveBlockDown()){
                try {
                    Thread.sleep(wait);

                } catch (InterruptedException ex) {
                    Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            wait=wait-50;
            if(i==3){i=0;}
            else{i++;}
        }
    }
    public int[][] nextBlock(){
            return arr[i];
    }
}
