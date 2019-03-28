package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class DiceActivity extends Activity {

    ImageView dice;
    SensorManager shakingSensor;
    Sensor shakingAccelerometer;


    private Random randomGenerator = new Random();
    private static int SHAKE_THRESHOLD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        dice = findViewById(R.id.dice);
        shakingSensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakingAccelerometer = shakingSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


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

        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        shakingSensor.registerListener(shakingListener,
                shakingSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        shakingSensor.unregisterListener(shakingListener);
        super.onPause();
    }


    public void rollDice() {

        int randomNumber = randomGenerator.nextInt(6) + 1;
        String number = "0";

        switch (randomNumber) {
            case 1:
                dice.setImageResource(R.drawable.dice1);
                number = "1";
                break;
            case 2:
                dice.setImageResource(R.drawable.dice2);
                number = "2";
                break;
            case 3:
                dice.setImageResource(R.drawable.dice3);
                number = "3";
                break;
            case 4:
                dice.setImageResource(R.drawable.dice4);
                number = "4";
                break;
            case 5:
                dice.setImageResource(R.drawable.dice5);
                number = "5";
                break;
            case 6:
                dice.setImageResource(R.drawable.dice6);
                number = "6";
                rolledNumber6();
                break;
        }

        Toast toast = Toast.makeText(getApplicationContext(), "You've got the number "+ number, Toast.LENGTH_LONG);
        toast.show();

        //Intent i = new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(i);


    }


    public void rolledNumber6() {

    }
}
