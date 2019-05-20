package com.example.di_1_hexentanz.gameboard.buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.di_1_hexentanz.R;

public class CustomButton extends View implements IButton {

    Paint p;
    DisplayMetrics metrics;
    int bitmapWidth;
    int bitMapHeight;
    int leftPosition;
    int topPosition;
    BtnType typeBtn;

    public CustomButton(Context context, DisplayMetrics metrics, BtnType typeBtn) {
        super(context);
        this.metrics = metrics;
        this.typeBtn = typeBtn;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p = new Paint();
        Bitmap b = getBitmap(typeBtn);
        bitMapHeight = b.getHeight() / 2;
        bitmapWidth = b.getWidth() / 2;

        if (typeBtn == BtnType.NO_BTN) {
            leftPosition = metrics.widthPixels / 2 + 10;
        } else if (typeBtn == BtnType.YES_BTN) {
            leftPosition = metrics.widthPixels / 2 - bitmapWidth - 10;
        } else if (typeBtn == BtnType.YOUR_TURN_BTN) {
            leftPosition = metrics.widthPixels / 2 - bitmapWidth / 2;
        }
        topPosition = metrics.heightPixels / 2 - bitMapHeight;
        Bitmap bResize = Bitmap.createScaledBitmap(b, bitmapWidth, bitMapHeight, false);
        canvas.drawBitmap(bResize, leftPosition, topPosition, p);
    }

    @Override
    public Bitmap getBitmap(BtnType typeBtn) {
        setTypeBtn(typeBtn);
        if (typeBtn == BtnType.YOUR_TURN_BTN) {
            return BitmapFactory.decodeResource(getResources(), R.drawable.btn_yourturn);

        } else if (typeBtn == BtnType.NO_BTN) {
            return BitmapFactory.decodeResource(getResources(), R.drawable.btn_no);
        }
        return BitmapFactory.decodeResource(getResources(), R.drawable.btn_yes);
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


    public void setTypeBtn(BtnType btnType) {
        this.typeBtn = btnType;
    }

    public BtnType getTypeBtn() {
        return typeBtn;
    }
}
