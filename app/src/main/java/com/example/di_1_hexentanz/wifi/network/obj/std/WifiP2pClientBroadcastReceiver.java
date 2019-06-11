package com.example.di_1_hexentanz.wifi.network.obj.std;


import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.TextView;

import com.example.di_1_hexentanz.JoinGameActivity;
import com.example.di_1_hexentanz.player.ColourChoosing;
import com.example.di_1_hexentanz.wifi.network.listener.std.WifiP2pPeerListListener;
import com.example.di_1_hexentanz.wifi.network.messages.MessageTag;
import com.example.di_1_hexentanz.wifi.network.obj.AbstractWifiP2pBroadcastReceiver;

public class WifiP2pClientBroadcastReceiver extends AbstractWifiP2pBroadcastReceiver {

    public WifiP2pClientBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter, TextView myDeviceView) {
        super(manager, channel, deviceListAdapter, myDeviceView);
    }

    @Override
    public void fillList(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter) {
        manager.requestPeers(channel, new WifiP2pPeerListListener(deviceListAdapter));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }
}
