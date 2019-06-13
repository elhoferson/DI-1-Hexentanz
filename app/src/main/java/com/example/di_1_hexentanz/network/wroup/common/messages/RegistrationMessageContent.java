package com.example.di_1_hexentanz.network.wroup.common.messages;


import com.example.di_1_hexentanz.network.wroup.common.WroupDevice;

public class RegistrationMessageContent {

    private WroupDevice wroupDevice;

    public WroupDevice getWroupDevice() {
        return wroupDevice;
    }

    public void setWroupDevice(WroupDevice wroupDevice) {
        this.wroupDevice = wroupDevice;
    }

}
