package com.example.di_1_hexentanz.obj.std;

import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class Player {

    public static final int PORT = 9872;

    private WifiP2pDevice myDevice;
    private Socket client;

    public Player(WifiP2pDevice myDevice) {
        this(myDevice, null);
    }

    public Player (WifiP2pDevice myDevice, WifiP2pDevice hostDevice){
        this.myDevice = myDevice;
        if (hostDevice != null) {
            try {
                client = new Socket(hostDevice.deviceAddress, PORT);
            } catch (IOException e) {
                Log.e("CLIENT", "connect to server", e);
            }
        }
    }

    public WifiP2pDevice getMyDevice() {
        return myDevice;
    }

    public Socket getClient() {
        return client;
    }
}
