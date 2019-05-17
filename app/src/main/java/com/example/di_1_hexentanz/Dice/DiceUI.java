package com.example.di_1_hexentanz.dice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.di_1_hexentanz.gameboard.GameState;
import com.example.di_1_hexentanz.gameboard.Gamescreen;
import com.example.di_1_hexentanz.R;


public class DiceUI extends AppCompatActivity {

    private ImageView dicePic;
    private DiceLogic dice;
    private boolean allWitchesOnBoard;
    private static int SHAKE_THRESHOLD = 8;
    private SensorManager shakingSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        dicePic = findViewById(R.id.dice);
        dice = new DiceLogic();
        setDiceImg(diceImg);


        shakingSensor = (SensorManager) getSystemService(SENSOR_SERVICE);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.allWitchesOnBoard = extras.getBoolean("allWitchesOnBoard");

        }


    }

    private final SensorEventListener shakingListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

            if (acceleration > SHAKE_THRESHOLD) {
                if (dice.rollDice() == 6) {
                    dicePic.setImageResource(diceImg[5]);
                    rolledNumber6();
                } else {
                    dicePic.setImageResource(diceImg[dice.rollDice() - 1]);
                    backToGamescreen();
                }
            }

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //not in use

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


    public void rolledNumber6() {
        this.onPause();
        AlertDialog.Builder popupNumber6 = new AlertDialog.Builder(this);
        if (allWitchesOnBoard) {
            popupNumber6.setTitle("Du hast eine 6 gewürfelt, entscheide deinen nächsten Zug!");
            popupNumber6.setPositiveButton("Farbe der Hexe anzeigen", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //show the colour of the witch
                    Gamescreen screen = new Gamescreen();
                    screen.setState(GameState.SHOW_WITCH_COLOURS);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", 6);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    screen.showWitchColours();

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


    @DrawableRes
    private int[] diceImg = new int[]{

            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6,

    };


    public void setDiceImg(int[] diceImg) {
        this.diceImg = diceImg;
    }


}
