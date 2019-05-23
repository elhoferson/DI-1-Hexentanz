package com.example.di_1_hexentanz.network.messages.std;

import com.example.di_1_hexentanz.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.network.messages.MessageTag;

public class TestMessage extends AbstractMessage {

    private String msg = "this is a test message";

    public TestMessage() {
        super(MessageTag.TEST);
    }

    public String getMsg() {
        return msg;
    }
}
