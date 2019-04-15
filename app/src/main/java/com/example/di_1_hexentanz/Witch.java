package com.example.di_1_hexentanz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

public class Witch extends FeldView {

    Feld[] felder = new Feld[36];
    Paint witch;


    public Witch(Context context, int x, int y, Feld feldInstanz, int number) {
        super(context, x, y, feldInstanz, number);
        witch = new Paint();

    }


}
