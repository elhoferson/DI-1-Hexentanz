package com.example.di_1_hexentanz.wifi.p2p.logic.std;

import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;

import com.example.di_1_hexentanz.obj.std.HostPlayer;
import com.example.di_1_hexentanz.obj.std.Player;

import java.util.List;

public class NetworkLogic {

    private static final String TAG = "NETWORK";
    private static NetworkLogic instance = null;
    private Type usageType;

    // if i'm the host host else it's null
    private HostPlayer host;

    // will be null if i'm the host
    private Player me;

    private NetworkLogic (){

    }

    public static void init(WifiP2pDevice hostDevice) {
        if (instance == null) {
            instance = new NetworkLogic();
            instance.setHost(new HostPlayer(hostDevice));
            instance.setUsageType(Type.HOST);
        }
    }

    public static void init(WifiP2pDevice hostDevice, WifiP2pDevice myDevice) {
        if (instance == null) {
            instance = new NetworkLogic();
            instance.setUsageType(Type.CLIENT);
            instance.setMe(new Player(myDevice, hostDevice));
        }
    }

    public static NetworkLogic getInstance() {
        return instance;
    }

    public Boolean addPlayers(WifiP2pDevice myDevice, List<WifiP2pDevice> devices) {
        if (getUsageType().equals(Type.CLIENT)) {
            Log.e(TAG, "Client not allowed to addPlayers");
            return false;
        }

        if (devices == null || devices.isEmpty()) {
            Log.e(TAG, "No devices");
            return false;
        }

        for (WifiP2pDevice device : devices) {
            Player newPlayer = new Player(device);
            host.getClients().clear();
            host.getClients().add(newPlayer);
        }
        return true;
    }

    public static void close() {
        instance = null;
    }

    private void setHost(HostPlayer host) {
        this.host = host;
    }

    private void setUsageType(Type usageType) {
        this.usageType = usageType;
    }

    private Type getUsageType() {
        return usageType;
    }

    private void setMe(Player me) {
        this.me = me;
    }

    private enum Type {
        HOST,
        CLIENT
    }
}
