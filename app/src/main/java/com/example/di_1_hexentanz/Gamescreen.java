package com.example.di_1_hexentanz;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Gamescreen extends AppCompatActivity {

    Feld[] felder = new Feld[36];
    int width;
    int height;
    private static final int FIELD_SIZE = 36;
    ImageButton btn_dice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamescreen);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (displayMetrics.heightPixels / 2) - 80;
        int width = displayMetrics.widthPixels / 2;


        for (int i = 0; i < 13; i++) {
            felder[i] = new Feld(i, width - 600 + i * 100, height + 300, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 13; i < 18; i++) {
            felder[i] = new Feld(i, width + 600, height - 300 - (i - 18) * 100, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 18; i < 31; i++) {
            felder[i] = new Feld(i, width + 600 - (i - 18) * 100, height - 300, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 31; i < 36; i++) {
            felder[i] = new Feld(i, width - 600, height - 200 + (i - 31) * 100, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        TouchableSurface surface = new TouchableSurface(getApplicationContext(), felder, btn_dice);
        addContentView(surface, findViewById(R.id.contraintLayout).getLayoutParams());



    }



    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }




    public void yourTurn(View v) {
        Intent dice = new Intent(this, Dice.class);
        startActivity(dice);

    }



    /*
   private void moveWitch() {
       Dice dice = new Dice();
       //Figur figur = new Figur();

       int step = dice.rollDice();
       //int newPos = figur.getPos() + step;

      '/' if(newPos >= FIELD_SIZE) {

       }
   }
   */
}
