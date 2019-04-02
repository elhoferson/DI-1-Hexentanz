package com.example.di_1_hexentanz;

import android.graphics.Color;

import java.util.HashMap;

public class Player {

    private int ID = 0;
    private static int IDCounter = 0;
    private String name;
    //these variables could me modified to "cheat"
    private Color color;
    private int position;
    private int inHome;
    private int inGoal;

    public Player(String name, Color color) {
        this.ID = IDCounter;
        IDCounter++;
        this.name = name;
        this.color = color;

        this.position = 0;
        this.inGoal = 4; // this will depend on the amount of players.
        this.inGoal = 0;
    }

    /*TODO:
        - move Method
        - cheat Method?
        -
     */





    // GETTER AND SETTER
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getInHome() {
        return inHome;
    }

    public void setInHome(int inHome) {
        this.inHome = inHome;
    }

    public int getInGoal() {
        return inGoal;
    }

    public void setInGoal(int inGoal) {
        this.inGoal = inGoal;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
