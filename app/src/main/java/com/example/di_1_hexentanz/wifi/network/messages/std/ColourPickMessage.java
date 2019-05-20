package com.example.di_1_hexentanz.wifi.network.messages.std;

import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.wifi.network.messages.AbstractColorMessage;
import com.example.di_1_hexentanz.wifi.network.messages.MessageTag;

public class ColourPickMessage extends AbstractColorMessage {


    public ColourPickMessage(PlayerColor playerColor) {
        super(MessageTag.PICK_COLOR, playerColor);
    }
}
