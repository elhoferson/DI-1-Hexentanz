package com.example.di_1_hexentanz;

import com.example.di_1_hexentanz.Dice.DiceLogic;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class DiceTest {

    private DiceLogic testdice;
    private List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6);
    private int result;

    @Before
    public void setTestdice() {
        testdice = new DiceLogic();
    }

    @Test
    public void testCorrectRandomNumberAlgorithm() {
        int i = 0;
        do {
            if (range.contains(testdice.getRandomNumber())) {
                i++;
            } else throw new IllegalArgumentException("Dice number range is not from 1 to 6");

        } while (i < 1000);

    }


    @Test
    public void testCorrectResultForGamescreen() {
        int i = testdice.getRandomNumber();
        setResult(i);


        // assertTrue(testdice.getResult(), result);
    }


    private void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }
}







