package com.example.di_1_hexentanz.Dice;

import java.util.Random;

public class DiceLogic {

    private int result;
    private Random randomGenerator = new Random();


    public int rollDice() {
        int i = getRandomNumber();
        setResult(i);
        return i;

    }

    public int getRandomNumber() {
        return randomGenerator.nextInt(6) + 1;
    }

    public int getResult() {
        return result;
    }

    private void setResult(int result) {
        this.result = result;
    }

}
