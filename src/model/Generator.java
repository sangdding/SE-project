package model;

import java.util.Random;

public class Generator {
    private int percent1;
    private int percent2;
    private int percent;
    private int[] arr = new int[1000];

    public Generator(int gameMode) {
        int c = -1;
        int b = -1;
        switch (gameMode) {
            case 0: //Normal
                c = 142;
                b = 142;
                break;
            case 1: //easy
                c = 170;
                b = 114;
                break;
            case 2: //hard
                c = 114;
                b = 170;
                break;
        }
        this.percent1 = c;
        this.percent2 = b;
        this.percent = ((1000 - percent1 - percent2) - (1000 - percent1 - percent2) % 5) / 5;
        for (int i = 0; i < percent1; i++) {
            this.arr[i] = 0;
        }
        for (int i = percent1; i < percent1 + percent2; i++) {
            this.arr[i] = 1;
        }

        int a = 2;
        int i = percent1 + percent2;
        int count = 0;
        boolean aa = false;
        while (count < 5) {
            for (int k = 0; k < percent; k++) {
                this.arr[i + count * percent + k] = a;
                if (i + count * percent + k == 999) {
                    aa = true;
                    break;
                }
            }
            if (aa) {
                break;
            }
            count++;
            a++;
        }
        shake();
    }

    public void shake() {
        Random r = new Random();
        Random r2 = new Random();
        for (int i = 0; i < 1000; i++) {
            int rr1 = r.nextInt(1000);
            int rr2 = r2.nextInt(1000);
            if (rr1 != rr2) {
                if (this.arr[rr1] != this.arr[rr2]) {
                    int temp = this.arr[rr1];
                    this.arr[rr1] = this.arr[rr2];
                    this.arr[rr2] = temp;
                } else {
                    i--;
                }
            }
        }
    }

    public int[] getArr() {
        return arr;
    }

}
