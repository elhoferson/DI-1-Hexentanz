package com.example.di_1_hexentanz;

import com.example.di_1_hexentanz.Dice.DiceLogic;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DiceTest {

    private DiceLogic testdice;
    private List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6);

    @Before
    public void setTestdice() {
        testdice = new DiceLogic();
    }

    @Test
    public void testCorrectRandomNumberAlgorithm() {
        for (int i = 0; i <= 1000000; i++) {
            if (range.contains(testdice.getRandomNumber())) {
                break;
            }

            throw new IllegalArgumentException("Dice number range is only from 1 to 6");
        }
    }


    @Test
    public void testCorrectResultForGamescreen() {
        int i = testdice.getRandomNumber();

            assertEquals(testdice.getResult(), i);
        }
}







