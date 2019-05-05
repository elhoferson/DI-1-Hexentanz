package com.example.di_1_hexentanz.wifi.p2p.obj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pConnectionInfoListener;
import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pPeerListListener;
import com.example.di_1_hexentanz.wifi.p2p.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.wifi.p2p.obj.std.WifiP2pDeviceAdapter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

public abstract class AbstractWifiP2pBroadcastReceiver extends BroadcastReceiver implements IWifiP2pConstants {

    private WifiP2pDeviceAdapter deviceListAdapter;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WifiP2pConnectionInfoListener connectionInfo = new WifiP2pConnectionInfoListener();

    public AbstractWifiP2pBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter) {
        this.manager = manager;
        this.channel = channel;
        this.deviceListAdapter = deviceListAdapter;

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
                Log.i(WIFI_P2P_TAG, "WIFI P2p enabled");
            } else {
                // Wi-Fi P2P is not enabled
                Toast.makeText(deviceListAdapter.getContext(), "WIFI P2p disabled",   Toast.LENGTH_LONG).show();
                Log.e(WIFI_P2P_TAG, "WIFI P2p disabled");
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Log.i(WIFI_P2P_TAG, "WIFI P2p peers changed");
            fillList(manager, channel, deviceListAdapter);

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.i(WIFI_P2P_TAG, "WIFI P2p connection changed");
            //manager.requestConnectionInfo(channel, connectionInfo);
            manager.requestConnectionInfo(channel, new WifiP2pManager.ConnectionInfoListener() {
                @Override
                public void onConnectionInfoAvailable(WifiP2pInfo info) {
                    Log.i(WIFI_P2P_TAG, "connection info: "+ info.toString());
                }
            });

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

            WifiP2pDevice myDevice = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            Log.i(WIFI_P2P_TAG, "WIFI P2p device changed device: "+ myDevice.toString());


        }
    }

    public abstract void fillList(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter);
}
