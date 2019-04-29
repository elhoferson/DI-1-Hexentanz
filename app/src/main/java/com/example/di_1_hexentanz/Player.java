package com.example.di_1_hexentanz;

import java.util.ArrayList;

public class Player {
    private String name;
    private PlayerColor color;
    private int number;
    private ArrayList<Witch> witches;
    private Feld startFeld;
    private Feld zielFeld;

    public Player(String name, PlayerColor color, int number, Feld startFeld, Feld zielFeld) {
        this.name = name;
        this.color = color;
        this.number = number;
        this.startFeld = startFeld;
        this.zielFeld = zielFeld;
        this.witches = new ArrayList<>();
    }

    public void addWitch(Witch witch) {
        witches.add(witch);
    }

    public PlayerColor getColor() {
        return color;
    }

    public Feld getStartFeld() {
        return startFeld;
    }
}