package com.example.di_1_hexentanz;

import java.util.ArrayList;

public class Player {
    private String name;
    private PlayerColor color;
    private int number;
    private ArrayList<Witch> witches;
    private Feld startFeld;
    private Feld zielFeld;
    private int witchesinGoal;

    public Player(String name, PlayerColor color, int number, Feld startFeld, Feld zielFeld) {
        this.name = name;
        this.witchesinGoal = 0;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Witch> getWitches() {
        return witches;
    }

    public void setWitches(ArrayList<Witch> witches) {
        this.witches = witches;
    }

    public void setStartFeld(Feld startFeld) {
        this.startFeld = startFeld;
    }

    public Feld getZielFeld() {
        return zielFeld;
    }

    public void setZielFeld(Feld zielFeld) {
        this.zielFeld = zielFeld;
    }

    public int getWitchesinGoal() {
        return witchesinGoal;
    }

    public void setWitchesinGoal(int witchesinGoal) {
        this.witchesinGoal = witchesinGoal;
    }
}