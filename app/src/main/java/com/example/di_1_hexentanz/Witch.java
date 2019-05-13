package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Context;

public class Witch {

    /**
     * int currentField:
     * -1 ... Witch is at Home
     * 36 ... Witch is at finish
     */

    int number;
    Player player;
    Feld currentField;
    WitchView witchView;
    Dice dice;
    DetermineWinner2 goal;

    public Witch(int number, Player player, Context context, int radius) {
        this.number = number;
        this.player = player;
        this.witchView = new WitchView(context, player.getStartFeld().getX(), player.getStartFeld().getY(), radius, this);
        this.witchView.setColor(player.getColor());
    }

    public void putWitchOnGameboard(Activity activity) {
        currentField = player.getStartFeld();
        activity.addContentView(witchView, activity.findViewById(R.id.contraintLayout).getLayoutParams());
    }

    public void moveWitch(Feld destination) {
          witchView.moveView(destination.getX(), destination.getY());
          currentField = destination;



    }


}
