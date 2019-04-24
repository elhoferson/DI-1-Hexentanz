package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class TouchableSurface extends View {
    Feld[] felder;
    Context context;
    Activity activity;

    public TouchableSurface(final Context context, Feld[] felder) {
        super(context);
        this.felder = felder;
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
                            Witch testWitch = new Witch(0, new Player("name", PlayerColor.BLUE,1, felder[i], felder[15]), context);
                            testWitch.putWitchOnGameboard(activity);
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


            switch (event.getActionButton()) {
                case MotionEvent.ACTION_BUTTON_PRESS:
                    Intent i = new Intent(getContext(), Dice.class);
                    i.getAction();

                    return true;




            }


            return false;

        }


    };



}
