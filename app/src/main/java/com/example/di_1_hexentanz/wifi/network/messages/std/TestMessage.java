package com.example.di_1_hexentanz.wifi.network.messages.std;

import com.example.di_1_hexentanz.wifi.network.messages.AbstractMessage;

public class TestMessage extends AbstractMessage {

    private String msg = "this is a test message";

    public TestMessage() {
        super(TEST_MESSAGE);
    }

    public String getMsg() {
        return msg;
    }
}
