package com.example.di_1_hexentanz.network.wroup.common.messages;


import com.example.di_1_hexentanz.network.wroup.common.WroupDevice;

public class DisconnectionMessageContent {

    private WroupDevice wroupDevice;


    public void setWroupDevice(WroupDevice wroupDevice) {
        this.wroupDevice = wroupDevice;
    }

    public WroupDevice getWroupDevice() {
        return wroupDevice;
    }

}
