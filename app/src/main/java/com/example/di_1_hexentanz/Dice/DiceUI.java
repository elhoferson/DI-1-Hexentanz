package com.example.di_1_hexentanz.Dice;

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

import com.example.di_1_hexentanz.R;


public class DiceUI extends AppCompatActivity implements SensorEventListener {

    DiceUI view;
    ImageView dicePic;
    RollDice dice;
    private boolean allWitchesOnBoard;
    int result;
    private static int SHAKE_THRESHOLD = 8;
    SensorManager shakingSensor;
    SensorEventListener shakingListener;
    Sensor shakingAccelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        dice = new RollDice();
        dicePic = findViewById(R.id.dice);

        shakingSensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakingAccelerometer = shakingSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        shakingSensor.registerListener(this, shakingAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.allWitchesOnBoard = extras.getBoolean("allWitchesOnBoard");

        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

        if (acceleration > SHAKE_THRESHOLD) {
            dice.rollDice();
        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

        //not necessary for our game

    }


    @Override
    protected void onResume() {

        super.onResume();

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        shakingSensor.registerListener(this, shakingAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        shakingSensor.unregisterListener(shakingListener);
        super.onPause();
    }


    public void rolledNumber6() {
        this.onPause();
        AlertDialog.Builder popupNumber6 = new AlertDialog.Builder(this);
        if (allWitchesOnBoard) {
            popupNumber6.setTitle("Du hast eine 6 gewürfelt, entscheide deinen nächsten Zug!");
            popupNumber6.setPositiveButton("Farbe der Hexe anzeigen", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //show the colour of the witch
                }
            })
                    .setNegativeButton("6 Felder gehen", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", 6);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();


                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();


        } else {
            backToGamescreen();
        }
    }


    public void backToGamescreen() {
        //Pause if number has been generated
        this.onPause();
        AlertDialog.Builder rolledNumber = new AlertDialog.Builder(this);
        rolledNumber.setTitle("Du hast eine " + dice.getResult() + " gewürfelt!");

        if (allWitchesOnBoard) {
            rolledNumber.setPositiveButton("Hexe auswählen und bewegen", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    goBackAndSendResult();
                }
            })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        } else {
            rolledNumber.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    goBackAndSendResult();
                }
            })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();

        }

    }


    private void goBackAndSendResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", dice.getResult());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


}
