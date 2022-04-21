package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {

    private Generator generatorEasy;
    private Generator generatorNormal;
    private Generator generatorHard;


    @BeforeEach
    void init() {
        generatorEasy = new Generator(1);
        generatorNormal = new Generator(0);
        generatorHard = new Generator(2);
    }

    @RepeatedTest(3)
    @Test
    void generator() {
        int[] easy = generatorEasy.getArr();
        int[] normal = generatorNormal.getArr();
        int[] hard = generatorHard.getArr();
        int countEasy = 0;
        int countNormal = 0;
        int countHard = 0;
        for (int a : easy) {
            if(a == 0) countEasy++;
        }
        for (int a : normal) {
            if(a == 0) countNormal++;
        }
        for (int a : hard) {
            if(a == 0) countHard++;
        }
        float result = (float) countEasy / (float) countNormal;
        System.out.println("노말모드 이지모드 생성 비율: " + result);
        result = (float) countNormal / (float) countHard;
        System.out.println("노말모드 하드모드 생성 비율: " + result);
    }
}