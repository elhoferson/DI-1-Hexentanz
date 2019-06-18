package com.example.di_1_hexentanz.gameboard;

import android.content.Context;

import java.io.Serializable;

public class Feld implements Serializable {
    private int number;
    int x;
    int y;
    int radius;

    public Feld(int number, int x, int y, int radius, Context context) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.radius = radius;
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

}
