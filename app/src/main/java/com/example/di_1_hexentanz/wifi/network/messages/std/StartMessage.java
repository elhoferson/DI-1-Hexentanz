package com.example.di_1_hexentanz.wifi.network.messages.std;

import android.content.Context;
import android.content.Intent;

import com.example.di_1_hexentanz.wifi.network.messages.AbstractMessage;
import com.example.di_1_hexentanz.wifi.network.messages.MessageTag;

public class StartMessage extends AbstractMessage {
    public StartMessage() {
        super(MessageTag.START);

    }

}
