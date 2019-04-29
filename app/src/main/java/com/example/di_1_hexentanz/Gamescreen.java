package com.example.di_1_hexentanz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Gamescreen extends AppCompatActivity {

    Feld[] felder = new Feld[36];
    ImageButton btn_dice;
    private static PlayerColor color;

    public static void setColor(PlayerColor color){
        Gamescreen.color = color;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamescreen);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        drawBoardGame();

        TouchableSurface surface = new TouchableSurface(getApplicationContext(), felder, this, btn_dice, color);
        addContentView(surface,findViewById(R.id.contraintLayout).getLayoutParams());



        ImageButton btn_dice = findViewById(R.id.btnYourTurn);
        btn_dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dice.class);
                startActivity(intent);
            }
        });


    }




    private void drawBoardGame() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (displayMetrics.heightPixels/2)-80;
        int width = displayMetrics.widthPixels/2;

        for (int i = 0; i < 13; i++) {
            felder[i] = new Feld(i, width-600+i*100, height+300, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 13; i < 18; i++) {
            felder[i] = new Feld(i, width+600, height-300-(i-18)*100, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 18; i < 31; i++) {
            felder[i] = new Feld(i, width+600-(i-18)*100, height-300, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 31; i < 36; i++) {
            felder[i] = new Feld(i, width-600, height-200+(i-31)*100, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }
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

}
