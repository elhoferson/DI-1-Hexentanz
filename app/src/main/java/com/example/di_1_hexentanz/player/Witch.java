package com.example.di_1_hexentanz.player;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import com.example.di_1_hexentanz.gameboard.Feld;
import com.example.di_1_hexentanz.R;
import com.example.di_1_hexentanz.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.network.messages.std.MoveMessage;

public class Witch {


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
        Feld msgCurrentField = currentField;
        currentField = destination;
        witchView.moveView(destination.getX(), destination.getY());
        activity.addContentView(witchView, activity.findViewById(R.id.contraintLayout).getLayoutParams());
        NetworkLogic.getInstance().sendMessageToHost(new MoveMessage(msgCurrentField, destination));
    }

    public void moveWitch(Feld destination) {
        mediaPlayer.start();
        witchView.moveView(destination.getX(), destination.getY());
        Feld msgCurrentField = currentField;
        currentField = destination;
        NetworkLogic.getInstance().sendMessageToHost(new MoveMessage(msgCurrentField, destination));
    }

    public void showColor() {
        witchView.showColor();
    }

    public void hideColor() {
        witchView.hideColor();
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setCurrentField(Feld currentField) {
        this.currentField = currentField;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
