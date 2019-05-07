package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Gamescreen extends AppCompatActivity {

    Witch[] witches = new Witch[6];
    Feld[] felder = new Feld[36];
    Witch selectedWitch;
    private static PlayerColor color;
    int height;
    int fieldRadius;
    int width;
    int fieldwidth;
    DisplayMetrics displayMetrics;
    boolean colorVisible = false;
    private GameState state;
    private int lastDiceResult;
    TouchableSurface surface;

    public int getLastDiceResult() {
        return lastDiceResult;
    }

    public void setLastDiceResult(int lastDiceResult) {
        this.lastDiceResult = lastDiceResult;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

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

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //State to start with
        state = GameState.MyTurn;

        findViewById(R.id.TestDisplay).setVisibility(View.INVISIBLE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = (displayMetrics.heightPixels/2)-80;
        width = displayMetrics.widthPixels/2;
        fieldRadius = width/20;
        fieldwidth = 2* fieldRadius +10;


        drawBoardGame();

        //rollDice();

        YourTurnButton yourTurnButton = new YourTurnButton(getApplicationContext(), displayMetrics);
        addContentView(yourTurnButton, findViewById(R.id.contraintLayout).getLayoutParams());

        YesButton yb = new YesButton(getApplicationContext(), displayMetrics);
        addContentView(yb, findViewById(R.id.contraintLayout).getLayoutParams());
        yb.setVisibility(View.INVISIBLE);

        NoButton nb = new NoButton(getApplicationContext(), displayMetrics);
        addContentView(nb, findViewById(R.id.contraintLayout).getLayoutParams());
        nb.setVisibility(View.INVISIBLE);

        surface = new TouchableSurface(getApplicationContext(), felder, yourTurnButton, yb, nb, this);
        surface.setColor(color);
        addContentView(surface, findViewById(R.id.contraintLayout).getLayoutParams());

        Button testButton = findViewById(R.id.button);
        testButton.bringToFront();
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (surface.isYourTurnButtonVisible()) {
                    surface.hideYourTurnButton();
                } else {
                    surface.showYourTurnButton();
                }
            }
        });

        Button testButton1 = findViewById(R.id.button2);
        testButton1.bringToFront();
        testButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colorVisible) {
                    for (int i = 0; i < witches.length; i++) {
                        witches[i].hideColor();
                    }
                    colorVisible = false;
                } else {
                    for (int i = 0; i < witches.length; i++) {
                        witches[i].showColor();
                    }
                    colorVisible = true;
                }
            }
        });

        Witch witch1 = new Witch(1, new Player("Player1", PlayerColor.BLUE,1, felder[0], felder[0]), getApplicationContext(), fieldRadius);
        witch1.putWitchOnGameboard(this);
        witches[0] = witch1;

        Witch witch2 = new Witch(2, new Player("Player2", PlayerColor.RED,2, felder[3], felder[0]), getApplicationContext(), fieldRadius);
        witch2.putWitchOnGameboard(this);
        witches[1] = witch2;

        Witch witch3 = new Witch(3, new Player("Player2", PlayerColor.YELLOW,3, felder[10], felder[0]), getApplicationContext(), fieldRadius);
        witch3.putWitchOnGameboard(this);
        witches[2] = witch3;

        Witch witch4 = new Witch(4, new Player("Player2", PlayerColor.PINK,4, felder[33], felder[0]), getApplicationContext(), fieldRadius);
        witch4.putWitchOnGameboard(this);
        witches[3] = witch4;

        Witch witch5 = new Witch(5, new Player("Player2", PlayerColor.GREEN,5, felder[27], felder[0]), getApplicationContext(), fieldRadius);
        witch5.putWitchOnGameboard(this);
        witches[4] = witch5;

        Witch witch6 = new Witch(6, new Player("Player2", PlayerColor.ORANGE,6, felder[12], felder[0]), getApplicationContext(), fieldRadius);
        witch6.putWitchOnGameboard(this);
        witches[5] = witch6;



        /*
        Witch testWitch;

        switch(color){
            case BLUE:
                testWitch = new Witch(0, new Player("name", PlayerColor.BLUE,1, felder[0], felder[35]),getApplicationContext(), fieldRadius);
                break;

            case GREEN:
                testWitch = new Witch(0, new Player("name", PlayerColor.GREEN,2, felder[12], felder[11]),getApplicationContext(), fieldRadius);
                break;

            case YELLOW:
                testWitch = new Witch(0, new Player("name", PlayerColor.YELLOW,3, felder[18], felder[17]),getApplicationContext(), fieldRadius);
                break;

            case RED:
                testWitch = new Witch(0, new Player("name", PlayerColor.RED,4, felder[30], felder[29]),getApplicationContext(), fieldRadius);
                break;

            default:
                throw new RuntimeException("unreachable case");
        }
        testWitch.putWitchOnGameboard(this);

        surface.setSelectedWitch(testWitch);*/
    }
/*
    private void rollDice() {
        ImageButton btn_dice = findViewById(R.id.btnYourTurn);
        btn_dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dice.class);
                startActivityForResult(intent, 1);
            }
        });
    }*/

public void startDice() {

}



    private void drawBoardGame() {

        for (int i = 0; i < 13; i++) {
            felder[i] = new Feld(i, width-(6*fieldwidth)+i*fieldwidth, height +(3*fieldwidth), fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 13; i < 18; i++) {
            felder[i] = new Feld(i, width+(6*fieldwidth), height -(3*fieldwidth)-(i-18)*fieldwidth, fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 18; i < 31; i++) {
            felder[i] = new Feld(i, width+(6*fieldwidth)-(i-18)*fieldwidth, height -(3*fieldwidth), fieldRadius, getApplicationContext());
            addContentView(felder[i].getFeldView(), findViewById(R.id.contraintLayout).getLayoutParams());
        }

        for (int i = 31; i < 36; i++) {
            felder[i] = new Feld(i, width-(6*fieldwidth), height -(2*fieldwidth)+(i-31)*fieldwidth, fieldRadius, getApplicationContext());
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

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                int result=data.getIntExtra("result", -1);
                lastDiceResult = result;
                state = GameState.SelectWitch;
                surface.hideYourTurnButton();
                TextView output = findViewById(R.id.TestDisplay);
                String outputText = "Bewege eine Hexe um " + lastDiceResult + "Felder!";
                output.setText(outputText);
                output.setVisibility(View.VISIBLE);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void returnToWitchSelection() {
        state = GameState.SelectWitch;
        surface.hideYourTurnButton();
        TextView output = findViewById(R.id.TestDisplay);
        String outputText = "Bewege eine Hexe um " + lastDiceResult + "Felder!";
        output.setText(outputText);
        output.setVisibility(View.VISIBLE);
    }

    public Witch[] getWitches() {
        return witches;
    }

    public void step2(int result) {
        selectedWitch.moveWitch(felder[result]);
    }


    public void witchSelected(Witch witch, YesButton yb, NoButton nb) {
        setState(GameState.ConfirmSelection);
        TextView outputtext = findViewById(R.id.TestDisplay);
        outputtext.setText("Diese Hexe bewegen?");
        yb.setVisibility(View.VISIBLE);
        nb.setVisibility(View.VISIBLE);

    }
}