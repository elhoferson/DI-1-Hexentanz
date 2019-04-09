package com.example.di_1_hexentanz.wifi.p2p.listener.std;

import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.p2p.obj.IWifiP2pConstants;

public class WifiP2pGroupInfoListener implements WifiP2pManager.GroupInfoListener, IWifiP2pConstants {

    @Override
    public void onGroupInfoAvailable(WifiP2pGroup group) {
        if (group == null) {
            return;
        }
        Log.i(WIFI_P2P_TAG, "Group: " + group.toString());
    }

}
