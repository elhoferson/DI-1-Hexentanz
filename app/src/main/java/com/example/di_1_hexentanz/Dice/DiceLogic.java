package com.example.di_1_hexentanz.Dice;

import java.util.Random;

public class DiceLogic {

    private int result;
    private Random randomGenerator = new Random();


    /*
    public int rollDice() {
        int i = getRandomNumber();
        setResult(i);
        return i;

    }
    */

    public int rollDice() {
       int randomnumber = randomGenerator.nextInt(6) + 1;
       return randomnumber;
    }

    public int getResult() {
        return result;
    }

    private void setResult(int result) {
        this.result = result;
    }

}
