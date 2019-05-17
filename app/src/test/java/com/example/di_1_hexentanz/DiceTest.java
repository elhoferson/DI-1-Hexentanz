package com.example.di_1_hexentanz;

import com.example.di_1_hexentanz.dice.DiceLogic;

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
    public void testCorrectDice() {
        int i = 0;
        do {
            if (range.contains(testdice.rollDice())) {
                i++;
            } else throw new IllegalArgumentException("Dice number range is not from 1 to 6");

        } while (i < 1000);

    }


    @Test
    public void testGetResult() {
        testdice.setResult(1);
        assertEquals(testdice.getResult(), 1);
    }

}







