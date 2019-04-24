package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class TouchableSurface extends View {
    Feld[] felder;
    Context context;
    Activity activity;
    ImageButton btn_dice;

    public TouchableSurface(final Context context, Feld[] felder, Activity activity, ImageButton btn_dice) {
        super(context);
        this.felder = felder;
        this.context = context;
        this.activity = activity;
        this.btn_dice = btn_dice;
        this.setOnTouchListener(handleTouch);
        //this.setOnTouchListener(yourTurn);
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




/*
    private View.OnClickListener yourTurn = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent dice = new Intent(v.getContext(), Dice.class);
            v.getContext().startActivity(dice);

        }
    };

    */






/*

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

    */







}
