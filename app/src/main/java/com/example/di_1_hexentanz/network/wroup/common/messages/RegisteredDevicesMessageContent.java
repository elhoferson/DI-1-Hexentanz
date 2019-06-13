package com.example.di_1_hexentanz.network.wroup.common.messages;



import com.example.di_1_hexentanz.network.wroup.common.WroupDevice;

import java.util.List;

public class RegisteredDevicesMessageContent {

    private List<WroupDevice> devicesRegistered;

    public List<WroupDevice> getDevicesRegistered() {
        return devicesRegistered;
    }

    public void setDevicesRegistered(List<WroupDevice> devicesRegistered) {
        this.devicesRegistered = devicesRegistered;
    }

}
