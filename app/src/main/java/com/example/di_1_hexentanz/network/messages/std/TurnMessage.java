package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.player.PlayerColor;
import com.example.di_1_hexentanz.network.messages.AbstractColorMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;

public class TurnMessage extends AbstractColorMessage {

    /**
     *
     * @param color
     */

    public TurnMessage(PlayerColor color) {
        super(MessageTag.TURN, color);
    }
}
