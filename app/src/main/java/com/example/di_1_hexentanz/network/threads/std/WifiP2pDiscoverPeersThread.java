package com.example.di_1_hexentanz.network.threads.std;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.di_1_hexentanz.network.obj.IWifiP2pConstants;

public class WifiP2pDiscoverPeersThread extends Thread implements IWifiP2pConstants {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    public WifiP2pDiscoverPeersThread(WifiP2pManager manager, WifiP2pManager.Channel channel) {
        this.manager = manager;
        this.channel = channel;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
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

        manager.stopPeerDiscovery(channel, new WifiP2pManager.ActionListener() {
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
