package com.example.di_1_hexentanz.Dice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.di_1_hexentanz.GameBoard.Gamescreen;
import com.example.di_1_hexentanz.R;


public class DiceUI extends AppCompatActivity {

    ImageView mydice;
    DiceUI view;
    SensorManager shakingSensor;
    Sensor shakingAccelerometer;
    RollDice dice = new RollDice();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        setMydice();

        shakingSensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakingAccelerometer = shakingSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);


    }

    @Override
    protected void onResume() {
        super.onResume();
        shakingSensor.registerListener(dice.getShakingListener(),
                shakingSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        shakingSensor.unregisterListener(dice.getShakingListener());
        super.onPause();
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

    private void setMydice() {
        this.mydice = findViewById(R.id.dice);
    }

    public ImageView getMydice() {
        return mydice;
    }
}
