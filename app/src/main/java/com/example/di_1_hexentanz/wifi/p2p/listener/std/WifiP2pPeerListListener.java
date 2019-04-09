package com.example.di_1_hexentanz.wifi.p2p.listener.std;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.p2p.obj.IWifiP2pConstants;

public class WifiP2pPeerListListener implements WifiP2pManager.PeerListListener, IWifiP2pConstants {


    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        for (WifiP2pDevice peer : peers.getDeviceList()) {
             Log.i(WIFI_P2P_TAG, peer.toString());
        }
    }
}
