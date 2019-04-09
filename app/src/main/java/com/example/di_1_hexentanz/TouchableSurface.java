package com.example.di_1_hexentanz;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class TouchableSurface extends View {
    Feld[] felder;

    public TouchableSurface(final Context context, Feld[] felder) {
        super(context);
        this.felder = felder;
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
                            Toast.makeText(getContext(), "Feld " + i, Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
            }

            return false;
        }
    };
}
