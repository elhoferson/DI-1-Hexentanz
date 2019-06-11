package com.example.di_1_hexentanz.wifi.network.obj.std;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.di_1_hexentanz.player.ColourChoosing;
import com.example.di_1_hexentanz.wifi.network.listener.std.WifiP2pGroupInfoListener;
import com.example.di_1_hexentanz.wifi.network.obj.AbstractWifiP2pBroadcastReceiver;

public class WifiP2pServerBroadcastReceiver extends AbstractWifiP2pBroadcastReceiver {

    public WifiP2pServerBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter, TextView myDeviceView) {
        super(manager, channel, deviceListAdapter, myDeviceView);
    }

    @Override
    public void fillList(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pDeviceAdapter deviceListAdapter) {
        manager.requestGroupInfo(channel, new WifiP2pGroupInfoListener(deviceListAdapter));
    }


}
