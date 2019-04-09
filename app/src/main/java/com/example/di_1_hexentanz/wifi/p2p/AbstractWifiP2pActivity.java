package com.example.di_1_hexentanz.wifi.p2p;

import android.content.BroadcastReceiver;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pChannelListener;
import com.example.di_1_hexentanz.wifi.p2p.obj.std.WifiP2pBroadcastReceiver;
import com.example.di_1_hexentanz.wifi.p2p.obj.std.WifiP2pIntentFilter;

public abstract class AbstractWifiP2pActivity extends AppCompatActivity {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (WifiP2pManager) getApplicationContext().getSystemService(WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), new WifiP2pChannelListener());
        receiver = new WifiP2pBroadcastReceiver(manager, channel, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new WifiP2pIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public WifiP2pManager getManager() {
        return manager;
    }

    public WifiP2pManager.Channel getChannel() {
        return channel;
    }

}
