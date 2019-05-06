package com.example.di_1_hexentanz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;


public class FeldView extends View {
    int number;
    int x, y;
    Feld feldInstanz;
    Paint paint;


    public FeldView(final Context context, int x, int y, Feld feldInstanz, final int number) {
        super(context);
        this.x = x;
        this.y = y;
        this.feldInstanz = feldInstanz;
        this.number = number;
        paint = new Paint();
    }

    public void onDraw(Canvas canvas) {
        DisplayMetrics metrics = new DisplayMetrics();
        canvas.scale(metrics.heightPixels/1080,metrics.widthPixels/1920);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, 45, paint);
    }

}
