package com.example.di_1_hexentanz.GameBoard.CustomButtons;

import android.graphics.Bitmap;


public interface IButton {

    int getBitmapWidth();
    int getBitMapHeight();
    int getLeftPosition();
    int getTopPosition();

    Bitmap getBitmap(BtnType typeBtn);

    enum BtnType{
        YOUR_TURN_BTN, YES_BTN, NO_BTN
    }

}
