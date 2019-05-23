package com.example.di_1_hexentanz.network.messages;

import com.example.di_1_hexentanz.player.PlayerColor;

public abstract class AbstractColorMessage extends AbstractMessage {

    private PlayerColor playerColor;

    public AbstractColorMessage(MessageTag tag, PlayerColor playerColor) {
        super(tag);
        this.playerColor = playerColor;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }
}
