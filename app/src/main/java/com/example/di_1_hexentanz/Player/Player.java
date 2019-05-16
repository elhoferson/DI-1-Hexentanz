package com.example.di_1_hexentanz.Player;

import android.content.Context;

import com.example.di_1_hexentanz.GameBoard.Feld;


public class Player {
    private String name;
    private PlayerColor color;
    private int number;
    private Witch[]witches;
    private Feld startFeld;
    private Feld zielFeld;
    private int witchesAtHome;

    public Player(String name, PlayerColor color, int number, int maxWitches, Feld startFeld, Feld zielFeld) {
        this.name = name;
        this.color = color;
        this.number = number;
        this.startFeld = startFeld;
        this.zielFeld = zielFeld;
        this.witches = new Witch[maxWitches];
        this.witchesAtHome = maxWitches;

    }

    public void initWitches(Context context, int size) {
        for(int i = 0; i < witches.length; i++){
            this.witches[i] = new Witch((i+1), this, context, size);
        }
    }

    public Witch[] getWitches(){ return this.witches; }

    public PlayerColor getColor() {
        return color;
    }

    public Feld getStartFeld() {
        return startFeld;
    }

    public int getWitchesAtHome(){ return this.witchesAtHome; }

    public void setWitchesAtHome(int witchesAtHome){
        this.witchesAtHome = witchesAtHome;
    }

    public int getNumber() {
        return number;
    }


    public void setWitches(Witch[] witches) {
        this.witches = witches;
    }

}