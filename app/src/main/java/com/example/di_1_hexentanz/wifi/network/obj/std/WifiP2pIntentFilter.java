package com.example.di_1_hexentanz.wifi.network.obj.std;

import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;

public class WifiP2pIntentFilter extends IntentFilter {

    public WifiP2pIntentFilter() {
        super();

        // Indicates a change in the Wi-Fi P2P status.
        this.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        this.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        this.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        this.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }
}
