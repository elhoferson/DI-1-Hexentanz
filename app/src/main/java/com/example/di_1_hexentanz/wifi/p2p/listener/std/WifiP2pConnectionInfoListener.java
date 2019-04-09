package com.example.di_1_hexentanz.wifi.p2p.listener.std;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.p2p.obj.IWifiP2pConstants;

import java.net.InetAddress;

public class WifiP2pConnectionInfoListener implements WifiP2pManager.ConnectionInfoListener, IWifiP2pConstants {

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        // InetAddress from WifiP2pInfo struct.
        InetAddress groupOwnerAddress = info.groupOwnerAddress;
        Log.i(WIFI_P2P_TAG, info.toString());

        // After the group negotiation, we can determine the group owner
        // (server).
        if (info.groupFormed && info.isGroupOwner) {
            // Do whatever tasks are specific to the group owner.
            // One common case is creating a group owner thread and accepting
            // incoming connections.
            Log.i(WIFI_P2P_TAG, "I'am the owner");
        } else if (info.groupFormed) {
            Log.i(WIFI_P2P_TAG, "I'am a client and will connect to the owner");
            // The other device acts as the peer (client). In this case,
            // you'll want to create a peer thread that connects
            // to the group owner.
        }
    }
}
