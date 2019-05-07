package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TouchableSurface extends View {
    Feld[] felder;
    Context context;
    Gamescreen activity;
    Witch selectedWitch;
    private PlayerColor color;
    YourTurnButton ytb;
    private boolean yourTurnButtonVisible;

    public TouchableSurface(final Context context, Feld[] felder, YourTurnButton ytb, Gamescreen activity) {
        super(context);
        this.felder = felder;
        this.context = context;
        this.activity = activity;
        this.ytb = ytb;
        yourTurnButtonVisible = true;
        this.setOnTouchListener(handleTouch);
    }

    private View.OnTouchListener handleTouch = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (activity.getState() == GameState.SelectWitch) {
                    for (int i = 0; i < felder.length; i++) {
                        if (x < felder[i].getX() + 45 && x > felder[i].getX() - 45 && y < felder[i].getY() + 45 && y > felder[i].getY() - 45) {
                            for (int j = 0; j < activity.getWitches().length; j++) {
                                if (activity.getWitches()[j].currentField.getNumber() == felder[i].getNumber()) {
                                    selectWitch(activity.getWitches()[j]);
                                }
                            }
                        }
                    }
                }

                if (activity.getState() == GameState.MyTurn) {
                    if (x > ytb.getLeftPosition() &&
                            x < ytb.getLeftPosition()+ytb.getBitmapWidth() &&
                            y > ytb.getTopPosition() &&
                            y < ytb.getTopPosition()+ytb.getBitMapHeight() &&
                            yourTurnButtonVisible) {
                        Intent i = new Intent(activity.getApplicationContext(), Dice.class);
                        activity.startActivityForResult(i, 1);
                    }
                }
                return false;
            }


            return true;
        }
    };

    private void selectWitch(Witch witch) {
        selectedWitch = witch;
        activity.witchSelected(witch);
    }


    /*private View.OnTouchListener yourTurn = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {


            if (v.getId() == R.id.btnYourTurn) {
                    Intent i = new Intent(getContext(), Dice.class);
                    i.getAction();
                    performClick();

            }

            return false;

        }

    };*/


    @Override
    public boolean performClick() {
        super.performClick();

        return true;


    }


    public void setColor(PlayerColor color){
        this.color = color;
    }

    public PlayerColor getColor(){
        return this.color;
    }

    public void hideYourTurnButton() {
        ytb.setVisibility(INVISIBLE);
        yourTurnButtonVisible = false;
    }

    public void showYourTurnButton() {
        ytb.setVisibility(VISIBLE);
        yourTurnButtonVisible = true;
    }

    public boolean isYourTurnButtonVisible() {
        return yourTurnButtonVisible;
    }
}