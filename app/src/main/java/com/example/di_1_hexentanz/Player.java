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
    }

    public void addWitch(Witch witch) {
        witches.add(witch);
    }

    public PlayerColor getColor() {
        return color;
    }

    public Feld getStartFeld() {
        return startFeld;

import java.util.Vector;

public class Player {

    private int start = -1;
    private int playernr = -1;
    private String name = null;
    private Vector<Figur> figures = null;
    private FeldView fieldv;

    public Player(String name, int playernr) {
        this.start = playernr * 5;
        this.name = name;
        this.playernr = playernr;
        this.figures = new Vector<Figur>(4);

        for(int i=0; i<4; i++) {
            this.figures.add(new Figur(this));
        }
    }

    public boolean walk(int figurenrpos, int walkFields) {
        Figur figur = null;
        for(int i=0; i<figures.size(); i++) {
            figur = figures.get(i);
            if(figur.getPos()==figurenrpos)
                break;
        }
        if(figur==null || figurenrpos!=figur.getPos()) {
            return false;
        }


        //int newPos = Feld.get
        return true;

    }


    public int getPlayerNr() {
        return 0;

    }
}
