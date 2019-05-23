package com.example.di_1_hexentanz.network.messages;

import java.io.Serializable;

public class AbstractMessage implements Serializable {

    private MessageTag tag;

    public AbstractMessage(MessageTag tag) {
        this.tag = tag;
    }

    public MessageTag getTag() {
        return tag;
    }
}
