package com.example.di_1_hexentanz.Dice;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.example.di_1_hexentanz.R;

import java.util.Random;

public class RollDice {

    RollDice dice;
    private DiceUI diceUI;
    ImageView dicePic;
    private int result;

    public void setDicePic(ImageView dicePic) {
        this.dicePic = dicePic.findViewById(R.id.dice);
    }


    public ImageView getDicePic() {
        return dicePic;
    }

    private Random randomGenerator = new Random();



    public void rollDice() {

        switch (getRandomNumber()) {

                case 1:
                    getDicePic().setImageResource(R.drawable.dice1);
                    setResult(1);
                    diceUI.backToGamescreen();
                    break;
                case 2:
                    dicePic.setImageResource(R.drawable.dice2);
                    setResult(2);
                    diceUI.backToGamescreen();
                    break;
                case 3:
                    dicePic.setImageResource(R.drawable.dice3);
                    setResult(3);
                    diceUI.backToGamescreen();
                    break;
                case 4:
                    dicePic.setImageResource(R.drawable.dice4);
                    setResult(4);
                    diceUI.backToGamescreen();
                    break;
                case 5:
                    dicePic.setImageResource(R.drawable.dice5);
                    setResult(5);
                    diceUI.backToGamescreen();
                    break;
                case 6:
                    dicePic.setImageResource(R.drawable.dice6);
                    setResult(6);
                    diceUI.rolledNumber6();
                    break;

                default:
                    throw new RuntimeException("wrong mydice, unreachable");

            }



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
