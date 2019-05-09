package com.example.di_1_hexentanz.Dice;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageView;

import com.example.di_1_hexentanz.R;

import java.util.Random;

public class RollDice {

    private ImageView mydice;
    RollDice dice;
    private DiceUI diceView;

    
    private Random randomGenerator = new Random();
    private static int SHAKE_THRESHOLD = 8;



    private final SensorEventListener shakingListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;


            if (acceleration > SHAKE_THRESHOLD) {
                rollDice();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

            //not necessary for our game

        }


    };



    private void rollDice() {
            switch (getRandomNumber()) {

                case 1:
                    mydice.setImageResource(R.drawable.dice1);
                    diceView.backToGamescreen();
                case 2:
                    mydice.setImageResource(R.drawable.dice2);
                    diceView.backToGamescreen();
                case 3:
                    mydice.setImageResource(R.drawable.dice3);
                    diceView.backToGamescreen();
                case 4:
                    mydice.setImageResource(R.drawable.dice4);
                    diceView.backToGamescreen();
                case 5:
                    mydice.setImageResource(R.drawable.dice5);
                    diceView.backToGamescreen();
                case 6:
                    mydice.setImageResource(R.drawable.dice6);
                    diceView.rolledNumber6();

                default:
                    throw new RuntimeException("wrong mydice, unreachable");

            }

    }

    public int getRandomNumber() {
        return randomGenerator.nextInt(6) + 1;
    }

    public SensorEventListener getShakingListener() {
        return shakingListener;
    }

}
