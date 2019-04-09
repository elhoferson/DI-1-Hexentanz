package com.example.di_1_hexentanz.wifi.p2p.listener.std;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.p2p.obj.IWifiP2pConstants;

public class WifiP2pGroupListener implements WifiP2pManager.ActionListener, IWifiP2pConstants {

    private String action;

    public WifiP2pGroupListener(String action) {
        this.action = action;
    }

    @Override
    public void onSuccess() {
        Log.i(WIFI_P2P_TAG,"group "+action);
    }

    @Override
    public void onFailure(int reason) {
        Log.e(WIFI_P2P_TAG, "cannot "+action+" group with reason "+ reason);
    }
}
