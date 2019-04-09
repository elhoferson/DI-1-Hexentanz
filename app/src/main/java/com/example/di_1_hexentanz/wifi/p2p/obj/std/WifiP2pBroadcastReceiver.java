package com.example.di_1_hexentanz.wifi.p2p.obj.std;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.di_1_hexentanz.wifi.p2p.obj.IWifiP2pConstants;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pPeerListListener;

public class WifiP2pBroadcastReceiver extends BroadcastReceiver implements IWifiP2pConstants {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private AppCompatActivity activity;

    public WifiP2pBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, AppCompatActivity activity) {
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
                Log.i(WIFI_P2P_TAG, "WIFI P2p enabled");
            } else {
                // Wi-Fi P2P is not enabled
                Log.i(WIFI_P2P_TAG, "WIFI P2p disabled");
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Log.i(WIFI_P2P_TAG, "WIFI P2p peers changed");
            manager.requestPeers(channel, new WifiP2pPeerListListener());

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.i(WIFI_P2P_TAG, "WIFI P2p connection changed");
            // Connection state changed! We should probably do something about
            // that.

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            Log.i(WIFI_P2P_TAG, "WIFI P2p device changed");
            //DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
            //        .findFragmentById(R.id.frag_list);
            //fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
            //WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));

        }
    }


}
