package com.example.di_1_hexentanz;

import android.content.Context;

public class Feld {
    int number;
    int x;
    int y;
    FeldView feldView;

    public Feld(int number, int x, int y, Context context) {
        this.number = number;
        this.x = x;
        this.y = y;
        feldView = new FeldView(context, x, y, this, number);
    }

    public FeldView getFeldView() {
        return feldView;
    }
}
