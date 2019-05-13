package com.example.di_1_hexentanz.wifi.p2p.obj.std;

import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.di_1_hexentanz.wifi.p2p.listener.std.WifiP2pPeerListListener;
import com.example.di_1_hexentanz.wifi.p2p.obj.AbstractWifiP2pBroadcastReceiver;

public class WifiP2pClientBroadcastReceiver extends AbstractWifiP2pBroadcastReceiver {

    public WifiP2pClientBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter, TextView myDeviceView) {
        super(manager, channel, deviceListAdapter, myDeviceView);
    }

    @Override
    public void fillList(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter) {
        manager.requestPeers(channel, new WifiP2pPeerListListener(deviceListAdapter));
    }
}
