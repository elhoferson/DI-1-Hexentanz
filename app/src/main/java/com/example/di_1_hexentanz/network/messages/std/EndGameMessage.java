package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.network.messages.AbstractColorMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;
import com.example.di_1_hexentanz.player.PlayerColor;

public class EndGameMessage extends AbstractColorMessage {





    public EndGameMessage(PlayerColor color) {
        super(MessageTag.END_GAME, color);
    }


}
