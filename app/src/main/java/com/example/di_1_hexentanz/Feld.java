package com.example.di_1_hexentanz;

import android.content.Context;

public class Feld {
    private int number;
    int x;
    int y;
    int radius;
    FeldView feldView;

    public Feld(int number, int x, int y, int radius, Context context) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.radius = radius;
        feldView = new FeldView(context, x, y, radius, this, number);
    }

    public FeldView getFeldView() {
        return feldView;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumber() {
        return number;
    }

    public void highlight() {
        feldView.highlight();
    }

    public void unhighlight() {
        feldView.unhighlight();
    }
}
