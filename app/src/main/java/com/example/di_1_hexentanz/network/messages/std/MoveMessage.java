package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.gameboard.Feld;
import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;
import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.player.Witch;

public class MoveMessage extends AbstractMessage {

    private Feld currentfield;
    private Feld destinationfield;

    public MoveMessage(Feld currentfield, Feld destinationfield) {
        super(MessageTag.MOVE_WITCH);
        this.currentfield = currentfield;
        this.destinationfield = destinationfield;
    }

    public Feld getDestinationfield() {
        return destinationfield;
    }

    public Feld getCurrentfield() {
        return currentfield;
    }
}
