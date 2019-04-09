package com.example.di_1_hexentanz.wifi.p2p.listener.std;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.p2p.obj.IWifiP2pConstants;

public class WifiP2pDiscoverPeersListener implements WifiP2pManager.ActionListener, IWifiP2pConstants {

    @Override
    public void onSuccess() {
        Log.i(WIFI_P2P_TAG,"successful discovering peers");
    }

    @Override
    public void onFailure(int reason) {
        Log.e(WIFI_P2P_TAG, "cannot discover peers with reason "+ reason);
    }
}
