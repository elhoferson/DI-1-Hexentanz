package com.example.di_1_hexentanz;

/**
 * representing the player with his witches and the pos on the board game
 * @author Chiara Szolderits
 */

public class Figur {

    private Player owner;
    private int pos;

    public Figur(Player owner) {
        this.owner = owner;
        this.pos = -1;
    }


    public void move(int newPos) {
        this.pos = newPos;
    }


    public int getPos() {
        return pos;
    }

    public Player getOwner() {
        return owner;
    }

    public void remove() {
        this.pos = -1;
    }

    public boolean onTarget() {
        return this.pos > 36 + (4 * owner.getPlayerNr());

    }

    public void onStart() {

    }



}
