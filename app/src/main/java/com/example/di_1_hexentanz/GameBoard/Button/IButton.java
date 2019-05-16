package com.example.di_1_hexentanz.GameBoard.Button;

import android.graphics.Bitmap;

public interface IButton {

    int getBitmapWidth();
    int getBitMapHeight();
    int getLeftPosition();
    int getTopPosition();

    Bitmap getBitmap();


}
