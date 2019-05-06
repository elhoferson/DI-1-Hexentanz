package com.example.di_1_hexentanz;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class DiceTest {

    Dice testDice;
    List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6);


    @Before
    public void setTestDice() {
        testDice = new Dice();
    }


    @Test
    public void testCorrectRandomNumberAlgorithm() {
        for (int i = 0; i <= 1000000; i++) {
            if (range.contains(testDice.getRandomNumber())) {
                break;
            }
            throw new IllegalArgumentException("Dice number range is only from 1 to 6");
        }
    }


}
