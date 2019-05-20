package com.example.di_1_hexentanz.wifi.network.messages.std;

import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.wifi.network.messages.AbstractColorMessage;
import com.example.di_1_hexentanz.wifi.network.messages.MessageTag;

public class TurnMessage extends AbstractColorMessage {

    public TurnMessage(PlayerColor color) {
        super(MessageTag.TURN, color);
    }
}
