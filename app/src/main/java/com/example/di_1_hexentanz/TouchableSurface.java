package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class TouchableSurface extends View {
    Feld[] felder;
    ImageButton dice;
    Gamescreen game;

    public TouchableSurface(final Context context, Feld[] felder, ImageButton dice) {
        super(context);
        this.felder = felder;
        this.dice = dice;
        this.setOnTouchListener(handleTouch);
        this.setOnTouchListener(yourTurn);

    }

    private View.OnTouchListener handleTouch = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    for (int i = 0; i < felder.length; i++) {
                        if (x < felder[i].getX()+45 && x > felder[i].getX()-45 && y < felder[i].getY()+45 && y > felder[i].getY()-45) {
                            Toast.makeText(getContext(), "Feld " + i, Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
            }

            return false;
        }
    };



    private View.OnTouchListener yourTurn = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            dice = findViewById(R.id.btnYourTurn);

            switch (event.getAction()) {
                case MotionEvent.ACTION_BUTTON_PRESS:
                    Intent i = new Intent(getContext(), Dice.class);
                    i.getAction();
                    return true;

            }
            return false;


        }

    };

}
