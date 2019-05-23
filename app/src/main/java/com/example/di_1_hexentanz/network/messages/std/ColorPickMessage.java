package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.network.messages.AbstractColorMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;

public class ColorPickMessage extends AbstractColorMessage {


    public ColorPickMessage(PlayerColor playerColor) {
        super(MessageTag.PICK_COLOR, playerColor);
    }
}
