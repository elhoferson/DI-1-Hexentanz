package com.example.di_1_hexentanz.dice;

import java.util.Random;

public class DiceLogic {

    private int result;
    private Random randomGenerator = new Random();


    public int rollDice() {
       result = randomGenerator.nextInt(6) + 1;
       setResult(result);
       return result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}