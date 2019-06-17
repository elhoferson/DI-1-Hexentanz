package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;

public class AskCheatMessage extends AbstractMessage {
    public AskCheatMessage(MessageTag tag) {
        super(MessageTag.ASK_FOR_CHEAT);
    }
}
