package com.example.di_1_hexentanz;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageView;

import java.util.Random;

public class RollDice extends DiceView {

    private ImageView mydice;
    private DiceView diceView;


    public RollDice(ImageView mydice, DiceView diceView) {
        this.mydice = mydice;
        this.diceView = diceView;
    }

    private Random randomGenerator = new Random();
    private static int SHAKE_THRESHOLD = 8;



    public final SensorEventListener shakingListener = new SensorEventListener() {
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

            //don't need it for our game

        }
    };





    public void rollDice() {

        switch (getRandomNumber()) {
            case 1:
                mydice.setImageResource(R.drawable.dice1);
                diceView.backToGamescreen();
                break;
            case 2:
                mydice.setImageResource(R.drawable.dice2);
                diceView.backToGamescreen();
                break;
            case 3:
                mydice.setImageResource(R.drawable.dice3);
                diceView.backToGamescreen();
                break;
            case 4:
                mydice.setImageResource(R.drawable.dice4);
                diceView.backToGamescreen();
                break;
            case 5:
                mydice.setImageResource(R.drawable.dice5);
                diceView.backToGamescreen();
                break;
            case 6:
                mydice.setImageResource(R.drawable.dice6);
                diceView.rolledNumber6();
                break;

            default:
                throw new RuntimeException("wrong mydice, unreachable");

        }

    }

    public int getRandomNumber() {
        return randomGenerator.nextInt(6) + 1;
    }



}
