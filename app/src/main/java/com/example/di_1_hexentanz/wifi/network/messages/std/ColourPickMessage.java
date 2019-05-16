package com.example.di_1_hexentanz.wifi.network.messages.std;

import com.example.di_1_hexentanz.PlayerColor;
import com.example.di_1_hexentanz.wifi.network.messages.AbstractMessage;

public class ColourPickMessage extends AbstractMessage {

    private PlayerColor color;

    public ColourPickMessage(PlayerColor color) {
        super(COLOUR_PICK);
        this.color = color;
    }
}
