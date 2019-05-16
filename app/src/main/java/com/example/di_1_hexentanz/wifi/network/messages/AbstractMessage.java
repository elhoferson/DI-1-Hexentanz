package com.example.di_1_hexentanz.wifi.network.messages;

public class AbstractMessage implements IMessage {

    private int tag;

    public AbstractMessage(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }
}
