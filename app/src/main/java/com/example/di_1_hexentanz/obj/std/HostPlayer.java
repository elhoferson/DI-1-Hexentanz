package com.example.di_1_hexentanz.obj.std;

import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class HostPlayer extends Player {

    private ServerSocket server;
    private List<Player> clients = new ArrayList<>();

    public HostPlayer(WifiP2pDevice myDevice) {
        super(myDevice);
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            Log.e("SERVER", "opening server socket", e);
            try {
                server.close();
            } catch (IOException e1) {
                Log.e("SERVER", "closing server socket", e1);
            }
        }
    }

    public List<Player> getClients() {
        return clients;
    }

    public ServerSocket getServer() {
        return server;
    }
}
