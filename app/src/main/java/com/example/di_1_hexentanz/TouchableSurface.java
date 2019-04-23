package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class TouchableSurface extends View {
    Feld[] felder;
    Context context;
    Activity activity;

    public TouchableSurface(final Context context, Feld[] felder, Activity activity) {
        super(context);
        this.felder = felder;
        this.context = context;
        this.activity = activity;
        this.setOnTouchListener(handleTouch);
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
}
