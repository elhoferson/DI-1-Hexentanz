package com.example.di_1_hexentanz;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DiceTest {

    Dice testDice;
    List<Integer> range = Arrays.asList(1,2,3,4,5,6);

    @Before
    public void setTestDice() {
        testDice = new Dice();
    }



    @Test
    public void testCorrectRandomNumberAlgorithm() {
        for(int i = 1 ; i<=100; i++) {
            //if(testDice.rollDice() < 1 || testDice.rollDice(); > 6) {
                System.out.println("Randoom number generator is working wrong");
        }

        }
    }

