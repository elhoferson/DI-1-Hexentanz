package com.example.di_1_hexentanz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class Dice extends AppCompatActivity {

    ImageView mydice;
    SensorManager shakingSensor;
    Sensor shakingAccelerometer;
    Witch witch;


    private Random randomGenerator = new Random();
    private static int SHAKE_THRESHOLD = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);



        mydice = findViewById(R.id.dice);
        shakingSensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakingAccelerometer = shakingSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);


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

            //don't need it for our game
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

        switch (getRandomNumber()) {
            case 1:
                mydice.setImageResource(R.drawable.dice1);
                backToGamescreen();
                break;
            case 2:
                mydice.setImageResource(R.drawable.dice2);
                backToGamescreen();
                break;
            case 3:
                mydice.setImageResource(R.drawable.dice3);
                backToGamescreen();
                break;
            case 4:
                mydice.setImageResource(R.drawable.dice4);
                backToGamescreen();
                break;
            case 5:
                mydice.setImageResource(R.drawable.dice5);
                backToGamescreen();
                break;
            case 6:
                mydice.setImageResource(R.drawable.dice6);
                rolledNumber6();
                break;

            default:
                throw new RuntimeException("wrong mydice, unreachable");

        }

    }

    public int getRandomNumber() {
        return randomGenerator.nextInt(6) + 1;
    }


    public void rolledNumber6() {
        AlertDialog.Builder popupNumber6 = new AlertDialog.Builder(this);
        popupNumber6.setTitle("Du hast eine 6 gewürfelt, entscheide deinen nächsten Zug!");
        popupNumber6.setPositiveButton("Farbe der Hexe anzeigen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //show the colour of the witch
            }
        })
                .setNegativeButton( "6 Felder gehen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), Gamescreen.class);
                startActivity(i);
            }
        })
        .setIcon(android.R.drawable.ic_dialog_info)
                .show();



    }

    public void backToGamescreen() {
        AlertDialog.Builder rolledNumber = new AlertDialog.Builder(this);
        rolledNumber.setNeutralButton("Hexe auswählen und bewegen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), Gamescreen.class);
                startActivity(i);
            }
        })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }


    public void sendResult(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", getRandomNumber());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }
}
