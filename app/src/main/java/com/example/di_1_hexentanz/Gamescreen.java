package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Gamescreen extends AppCompatActivity {

    Feld[] felder = new Feld[36];
    int result;
    Witch selectedWitch;
    Dice dice;
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

        rollDice();

        TouchableSurface surface = new TouchableSurface(getApplicationContext(), felder, this, dice);
        surface.setColor(color);
        addContentView(surface, findViewById(R.id.contraintLayout).getLayoutParams());

        Witch testWitch;
        switch(color){
            case BLUE:
                testWitch = new Witch(0, new Player("name", PlayerColor.BLUE,1, felder[0], felder[35]),getApplicationContext());
                break;

            case GREEN:
                testWitch = new Witch(0, new Player("name", PlayerColor.GREEN,2, felder[12], felder[11]),getApplicationContext());
                break;

            case YELLOW:
                testWitch = new Witch(0, new Player("name", PlayerColor.YELLOW,3, felder[18], felder[17]),getApplicationContext());
                break;

            case RED:
                testWitch = new Witch(0, new Player("name", PlayerColor.RED,4, felder[30], felder[29]),getApplicationContext());
                break;

            default:
                testWitch = null;
                break;
        }

        testWitch.putWitchOnGameboard(this);



        surface.setSelectedWitch(testWitch);

    }




    private void rollDice() {
        ImageButton btn_dice = findViewById(R.id.btnYourTurn);
        btn_dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dice.class);
                startActivityForResult(intent, 1);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                int result = data.getIntExtra("result", 0);
                step2(result);

            }
        }
    }


    public void step2(int result) {
        selectedWitch.moveWitch(felder[result]);
    }






}