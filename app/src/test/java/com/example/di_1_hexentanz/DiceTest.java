package com.example.di_1_hexentanz;

import com.example.di_1_hexentanz.Dice.Dice_old;
import com.example.di_1_hexentanz.Dice.RollDice;

import org.junit.Before;
import org.junit.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DiceTest {

    RollDice testdice;
    List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6);

    @Before
    public void setTestdice() {
        testdice = new RollDice();
    }

    @Test
    public void testCorrectRandomNumberAlgorithm() {
        for (int i = 0; i <= 1000000; i++) {
            if (range.contains(testdice.getRandomNumber())) {
                break;
            }
            throw new IllegalArgumentException("Dice_old number range is only from 1 to 6");
        }
    }

    @Test
    public void testCorrectResultForGamescreen() {

        if(testdice.getRandomNumber() == 1) {
            assertEquals(1, testdice.getResult());
        }
        else if(testdice.getRandomNumber() == 2) {
            assertEquals(2, testdice.getResult());
        }
        else if(testdice.getRandomNumber() == 3) {
            assertEquals(3, testdice.getResult());
        }
        else if(testdice.getRandomNumber() == 4) {
            assertEquals(4, testdice.getResult());
        }
        else if(testdice.getRandomNumber() == 5) {
            assertEquals(5, testdice.getResult());
        }
        else if(testdice.getRandomNumber() == 6) {
            assertEquals(6, testdice.getResult());
        }

    }

}







