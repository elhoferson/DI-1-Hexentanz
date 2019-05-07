package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class TouchableSurface extends View {
    Feld[] felder;
    Context context;
    Activity activity;
    Witch selectedWitch;
    private PlayerColor color;

    public TouchableSurface(final Context context, Feld[] felder, Activity activity, ImageView dice) {
        super(context);
        this.felder = felder;
        this.context = context;
        this.activity = activity;
        this.setOnTouchListener(handleTouch);
    }

    public void setSelectedWitch(Witch selectedWitch) {
        this.selectedWitch = selectedWitch;
    }

    private View.OnTouchListener handleTouch = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    for (int i = 0; i < felder.length; i++) {
                        if (x < felder[i].getX() + 45 && x > felder[i].getX() - 45 && y < felder[i].getY() + 45 && y > felder[i].getY() - 45) {

                            selectedWitch.moveWitch(felder[i]);

                            /*
                            int selected = selectedWitch.number;

                            int temp;

                            switch(mydice.getRandomNumber()) {

                                case 1:
                                    temp = selected+1;
                                    goOverStart(temp);
                                    selectedWitch.moveWitch(felder[temp]);

                                case 2:
                                    temp = selected + 2;
                                    goOverStart(temp);
                                    selectedWitch.moveWitch(felder[temp]);

                                case 3:
                                    temp = selected + 3;
                                    goOverStart(temp);
                                    selectedWitch.moveWitch(felder[temp]);

                                case 4:
                                    temp = selected + 4;
                                    goOverStart(temp);
                                    selectedWitch.moveWitch(felder[temp]);

                                case 5:
                                    temp = selected + 5;
                                    goOverStart(temp);
                                    selectedWitch.moveWitch(felder[temp]);

                                case 6:
                                    temp = selected + 6;
                                    goOverStart(temp);
                                    selectedWitch.moveWitch(felder[temp]);

                                default:
                                    selectedWitch.moveWitch(felder[i]);
                            }

                            */


                        }
                    }
                    return false;
            }


            return true;
        }
    };



    private void goOverStart(int temp) {
        if(temp >= 36) {
            selectedWitch.moveWitch(felder[1]);
        }
    }


    private View.OnTouchListener yourTurn = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {


            if (v.getId() == R.id.btnYourTurn) {
                Intent i = new Intent(getContext(), DiceView.class);
                i.getAction();
                performClick();

            }

            return false;
        }


    };


    @Override
    public boolean performClick() {
        super.performClick();

        return true;


    }


    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public PlayerColor getColor() {
        return this.color;
    }




}