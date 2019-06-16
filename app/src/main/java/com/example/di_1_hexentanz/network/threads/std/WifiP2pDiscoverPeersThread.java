package com.example.di_1_hexentanz.network.threads.std;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.di_1_hexentanz.network.logic.std.WifiP2pLogic;
import com.example.di_1_hexentanz.network.obj.IWifiP2pConstants;

public class WifiP2pDiscoverPeersThread extends Thread implements IWifiP2pConstants {

    @Override
    public void run() {
        while (!isInterrupted()) {
            WifiP2pLogic.instance().getManager().discoverPeers(WifiP2pLogic.instance().getChannel(), new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.i(WIFI_P2P_TAG,"successful discovering peers");
                }

                @Override
                public void onFailure(int reason) {
                    Log.e(WIFI_P2P_TAG, "cannot discover peers with reason "+ reason);
                }
            });
            try {
                sleep(8000);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        WifiP2pLogic.instance().getManager().stopPeerDiscovery(WifiP2pLogic.instance().getChannel(), new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(WIFI_P2P_TAG,"successful stopped discovering peers");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(WIFI_P2P_TAG, "cannot stop discover peers with reason "+ reason);
            }
        });
    }
}
