package model;
import java.util.Random;
public class Generator {
    private int percent1;
    private int percent2;
    private int percent;
    private int[] arr = new int[1000];
    public Generator(int percent1,int percent2){
        this.percent1=percent1;
        this.percent2=percent2;
        this.percent=((1000-percent1-percent2)-(1000-percent1-percent2)%5)/5;
        for(int i=0; i<percent1; i++){
            this.arr[i]=0;
        }
        for(int i=percent1; i<percent1+percent2;i++){
            this.arr[i]=1;
        }

        int a=2;
        int i=percent1+percent2;
        int count=0;
        boolean aa=false;
        while(count<5) {
            for (int k = 0; k < percent; k++) {
                this.arr[i +count*percent+ k] = a;
                if(i +count*percent+ k==999){
                    aa=true;
                    break;}
            }
            if(aa){break;}
            count++;
            a++;
        }
        shake();
    }
    public void shake(){
        Random r= new Random();
        Random r2= new Random();
        for(int i=0; i<1000; i++){
            int rr1=r.nextInt(1000);
            int rr2=r2.nextInt(1000);
            if(rr1 != rr2){
                if(this.arr[rr1]!=this.arr[rr2]){
                    int temp=this.arr[rr1];
                    this.arr[rr1]=this.arr[rr2];
                    this.arr[rr2]=temp;}
                else{i++;}
            }
        }
    }
    public void show(){
        for(int i=0; i<this.arr.length;i++){
            System.out.println(this.arr[i]);}
    }
    public void count(){
        int[] arr = new int[]{0,1,2,3,4,5,6};
        for(int i=0; i<this.arr.length; i++){
            switch(this.arr[i]){
                case 0:
                    arr[0]++;
                    break;
                case 1:
                    arr[1]++;
                    break;
                case 2:
                    arr[2]++;
                    break;
                case 3:
                    arr[3]++;
                    break;
                case 4:
                    arr[4]++;
                    break;
                case 5:
                    arr[5]++;
                    break;
                case 6:
                    arr[6]++;
                    break;
            }
        }
        for(int i=0; i< arr.length; i++){
            System.out.println(i+":"+arr[i]);}
    }
}
