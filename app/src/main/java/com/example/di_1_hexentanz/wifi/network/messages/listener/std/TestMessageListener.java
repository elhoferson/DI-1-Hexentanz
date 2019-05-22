package com.example.di_1_hexentanz.wifi.network.messages.listener.std;

import android.util.Log;

import com.example.di_1_hexentanz.wifi.network.messages.listener.AbstractMessageReceivedListener;
import com.example.di_1_hexentanz.wifi.network.messages.std.TestMessage;
import com.example.di_1_hexentanz.wifi.network.mordechaim_server.Server;

public class TestMessageListener extends AbstractMessageReceivedListener<TestMessage> {

    @Override
    public void handleReceivedMessage(Server server, Server.ConnectionToClient client, TestMessage msg) {
        Log.e("MSG", msg.getMsg());
    }

}
