package com.example.di_1_hexentanz.GameBoard.Button;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.di_1_hexentanz.R;

public class YourTurnIButton extends View implements IButton {
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
        Bitmap b = getBitmap();
        bitMapHeight = b.getHeight()/2;
        bitmapWidth = b.getWidth()/2;
        topPosition = metrics.heightPixels/2-bitMapHeight;
        leftPosition = metrics.widthPixels/2-bitmapWidth/2;
        Bitmap bResize = Bitmap.createScaledBitmap(b, bitmapWidth,bitMapHeight, false);
        canvas.drawBitmap(bResize, leftPosition, topPosition, p);
    }

    @Override
    public Bitmap getBitmap() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.btn_yourturn);
    }

    public YourTurnIButton(Context context, DisplayMetrics metrics) {
        super(context);
        this.metrics = metrics;
    }

    @Override
    public int getBitmapWidth() {
        return bitmapWidth;
    }

    @Override
    public int getBitMapHeight() {
        return bitMapHeight;
    }

    @Override
    public int getLeftPosition() {
        return leftPosition;
    }

    @Override
    public int getTopPosition() {
        return topPosition;
    }

}
