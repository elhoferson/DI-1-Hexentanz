package com.example.di_1_hexentanz.wifi.network.listener.std;

import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.network.obj.IWifiP2pConstants;
import com.example.di_1_hexentanz.wifi.network.obj.std.WifiP2pDeviceAdapter;

public class WifiP2pGroupInfoListener implements WifiP2pManager.GroupInfoListener, IWifiP2pConstants {

    private WifiP2pDeviceAdapter peerListAdapter;

    public WifiP2pGroupInfoListener(WifiP2pDeviceAdapter peerListAdapter) {
        this.peerListAdapter = peerListAdapter;
    }

    @Override
    public void onGroupInfoAvailable(WifiP2pGroup group) {
        if (group == null) {
            Log.i(WIFI_P2P_TAG, "no group found");
            return;
        } else {
            peerListAdapter.clear();
            peerListAdapter.addAll(group.getClientList());

            //NetworkLogic.getInstance().addPlayers(group.getClientList());
            Log.i(WIFI_P2P_TAG, "game is ready to start");
        }
        Log.i(WIFI_P2P_TAG, "Group: " + group.toString());
    }

}
