package com.example.di_1_hexentanz.wifi.p2p.listener.std;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.p2p.obj.IWifiP2pConstants;

public class WifiP2pChannelListener implements WifiP2pManager.ChannelListener, IWifiP2pConstants {
        
        @Override
        public void onChannelDisconnected() {
            Log.e(WIFI_P2P_TAG, "Channel disconneted");
        }
    }