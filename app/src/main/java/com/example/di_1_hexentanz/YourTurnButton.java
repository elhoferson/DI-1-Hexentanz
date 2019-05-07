package com.example.di_1_hexentanz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

public class YourTurnButton extends View {
    Paint p;
    DisplayMetrics metrics;
    int bitmapWidth;
    int bitMapHeight;
    int leftPosition;
    int topPosition;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p=new Paint();
        Bitmap b= BitmapFactory.decodeResource(getResources(), R.drawable.btn_yourturn);
        p.setColor(Color.RED);
        bitMapHeight = b.getHeight();
        bitmapWidth = b.getWidth();
        topPosition = metrics.heightPixels/2-bitMapHeight/2;
        leftPosition = metrics.widthPixels/2-bitmapWidth/4;
        Bitmap bResize = Bitmap.createScaledBitmap(b, bitmapWidth/2,bitMapHeight/2, false);
        canvas.drawBitmap(bResize, leftPosition, topPosition, p);
    }

    public YourTurnButton(Context context, DisplayMetrics metrics) {
        super(context);
        this.metrics = metrics;
    }

    public int getBitmapWidth() {
        return bitmapWidth;
    }

    public int getBitMapHeight() {
        return bitMapHeight;
    }

    public int getLeftPosition() {
        return leftPosition;
    }

    public int getTopPosition() {
        return topPosition;
    }
}
