package com.example.di_1_hexentanz;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

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
    int size;
    Dice dice;
    MediaPlayer mediaPlayer;

    public Feld getCurrentField() {
        return currentField;
    }

    public Witch(int number, Player player, Context context, int size) {
        this.number = number;
        this.player = player;
        this.witchView = new WitchView(context, player.getStartFeld().getX(), player.getStartFeld().getY(), size, this);
        this.witchView.setColor(player.getColor());
        mediaPlayer = MediaPlayer.create(context,R.raw.swoosh);
    }

    public void putWitchOnGameboard(Activity activity) {
        currentField = player.getStartFeld();
        activity.addContentView(witchView, activity.findViewById(R.id.contraintLayout).getLayoutParams());
    }

    public void moveWitch(Feld destination) {
        mediaPlayer.start();
          witchView.moveView(destination.getX(), destination.getY());
          currentField = destination;



    }

    public void showColor() {
        witchView.showColor();
    }

    public void hideColor() {
        witchView.hideColor();
    }
}
