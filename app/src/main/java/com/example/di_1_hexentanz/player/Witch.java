package com.example.di_1_hexentanz.player;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import com.example.di_1_hexentanz.gameboard.Feld;
import com.example.di_1_hexentanz.R;

public class Witch {

    /**
     * int currentField:
     * -1 ... Witch is at Home
     * 36 ... Witch is at finish
     */

    int number;
    Player player;
    public Feld currentField;
    public WitchView witchView;
    MediaPlayer mediaPlayer;

    public Feld getCurrentField() {
        return currentField;
    }

    public Witch(int number, Player player, Context context, int size) {
        this.number = number;
        this.player = player;
        this.witchView = new WitchView(context, player.getStartFeld().getX(), player.getStartFeld().getY(), size, this);
        this.witchView.setColor(player.getColor());
        mediaPlayer = MediaPlayer.create(context, R.raw.swoosh);
    }

    public void putWitchOnGameboard(Activity activity, Feld destination) {
        currentField = destination;
        witchView.moveView(destination.getX(), destination.getY());
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

    public Player getPlayer(){
        return this.player;
    }

}
