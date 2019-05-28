package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;

public class TurnMessage extends AbstractMessage {

    public TurnMessage() {
        super(MessageTag.TURN);
    }
}
