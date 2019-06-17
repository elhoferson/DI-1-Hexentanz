package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;

public class BeginTurnMessage extends AbstractMessage {

    public BeginTurnMessage() {
        super(MessageTag.BEGIN_TURN);
    }
}
