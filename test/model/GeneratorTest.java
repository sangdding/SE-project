package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {

    private Generator generator;


    @Test
    void init() {
        for (int i = 0; i < 3; i++) {
            generator = new Generator(i);
        }
    }
}