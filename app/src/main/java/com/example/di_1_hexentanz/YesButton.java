package com.example.di_1_hexentanz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

public class YesButton extends View {
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
        Bitmap b= BitmapFactory.decodeResource(getResources(), R.drawable.btn_yes);
        bitMapHeight = b.getHeight()/2;
        bitmapWidth = b.getWidth()/2;
        topPosition = metrics.heightPixels/2-bitMapHeight;
        leftPosition = metrics.widthPixels/2-bitmapWidth-10;
        Bitmap bResize = Bitmap.createScaledBitmap(b, bitmapWidth,bitMapHeight, false);
        canvas.drawBitmap(bResize, leftPosition, topPosition, p);
    }

    public YesButton(Context context, DisplayMetrics metrics) {
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
